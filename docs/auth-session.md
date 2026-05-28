# Authentifizierung: Session ohne Keycloak

Gültige Festlegung für **vbb-next** (Branch `feature/vbb-next`).

---

## Modell

| Aspekt | Festlegung |
|--------|------------|
| Provider | **Spring Security 6** (Spring Boot 4) |
| Identität | Benutzer in **PostgreSQL** (`users`, Rollen z. B. `roles` / `group`) |
| Passwörter | **BCrypt** – keine MD5-Hashes aus Legacy |
| SPA-Transport | **Session-Cookie** (HttpOnly, Secure in Prod, SameSite) |
| JWT / Keycloak | **nein** |
| API-Stil | REST unter `/api`; normale Calls mit `credentials: 'include'` |
| CSRF | **aktiv** (Spring-Security-Standard für Cookie-Sessions) |

---

## Ablauf

```
1. POST /api/auth/login     { "userid": "...", "password": "..." }
   → Spring Security authentifiziert
   → Set-Cookie: SESSION=… (HttpOnly)

2. GET/POST /api/…
   → Browser sendet Cookie automatisch
   → React: fetch(…, { credentials: 'include' })

3. POST /api/auth/logout
   → Session invalidieren, Cookie löschen
```

Rollen (`ADMIN`, `USER`, `READER`) aus der Datenbank → `UserDetails` → Method Security, z. B. `@PreAuthorize("hasRole('ADMIN')")`.

Die View **`V_USER_ROLE`** aus MySQL entfällt; Spring liest Rollen direkt aus den Tabellen.

---

## Registrierung

Analog Legacy (`register.xhtml`, `RegisterServlet`):

- `POST /api/auth/register` – Benutzer anlegen (Zustand z. B. unbestätigt)
- Bestätigungslink per E-Mail (Thymeleaf / Spring Mail)
- `GET` oder `POST /api/auth/confirm?regid=…` – Aktivierung

Konkrete Zustandsmaschine kann aus `RegistrationState` im Legacy-Code übernommen werden.

---

## Deployment: eine Domain (empfohlen)

```
https://vbb.example.de/           → React (static)
https://vbb.example.de/api/       → Spring Boot
```

| Einstellung | Prod |
|-------------|------|
| Cookie | `Secure`, `HttpOnly` |
| SameSite | `Lax` oder `Strict` |
| CSRF | Cookie + Header (Spring SPA-Pattern) |

Vorteil: kein `SameSite=None` nötig, einfacheres CORS-Verhalten.

---

## Entwicklung (Vite + Spring Boot)

**Option A (empfohlen):** Vite-Dev-Proxy

```javascript
// vite.config – Skizze
server: {
  proxy: {
    '/api': 'http://localhost:8080',
  },
}
```

React ruft `/api/…` auf derselben Origin (`localhost:5173`) auf → Proxy leitet weiter → Cookie wirkt wie „eine Site“.

**Option B:** getrennte Ports ohne Proxy

- CORS mit `allowCredentials: true` und fester Origin
- Cookie `SameSite=None; Secure` (HTTPS)
- CSRF konsequent konfigurieren

---

## Spring Security (Implementierung – Zielbild)

| Thema | Vorgehen |
|-------|----------|
| Login | REST-Controller oder `AuthenticationManager` + JSON-Login-Filter; **kein** klassisches `formLogin`-Redirect für SPA |
| Session | `SessionCreationPolicy.IF_REQUIRED` (Default) |
| Logout | `POST /api/auth/logout` |
| Stateless API | **nein** für eingeloggte Nutzer – Session ist stateful |
| Öffentliche Pfade | `/api/public/**`, `/api/auth/login`, `/api/auth/register`, statische Assets |
| Session-Store | eine Instanz: In-Memory ausreichend; bei Skalierung später **Spring Session + Redis** |

---

## React (Implementierung – Zielbild)

| Thema | Vorgehen |
|-------|----------|
| HTTP-Client | Axios oder fetch mit **`credentials: 'include'`** |
| CSRF | Token aus Cookie lesen (wenn von Spring exponiert) und als Header `X-XSRF-TOKEN` mitsenden |
| Auth-State | z. B. `GET /api/auth/me` nach Laden der App |
| Routing | Geschützte Routen nur nach erfolgreichem `me`; 401 → Redirect `/login` |
| Token-Speicher | **kein** localStorage für Session-IDs |

---

## Öffentlicher Saldo (Ausnahme)

| Legacy | Ziel |
|--------|------|
| `saldo.xhtml?token=…` | `GET /api/public/saldo?token=…` |
| Tabelle `TOKEN` | unverändert fachlich; Validierung im Service |

- **Keine** Session erforderlich.
- Rate-Limiting und Token-Ablauf später optional ergänzen.

---

## Sicherheits-Checkliste (Prod)

- [ ] HTTPS überall
- [ ] BCrypt für alle Passwörter
- [ ] CSRF für mutierende Requests (`POST`, `PUT`, `DELETE`)
- [ ] Rollen serverseitig prüfen (nicht nur UI ausblenden)
- [ ] `hibernate.show_sql` und sensible Logs vermeiden
- [ ] Session-Timeout konfigurieren

---

## Spätere Erweiterung (optional)

Wenn die API horizontal skaliert wird:

- **Spring Session** mit **Redis**
- oder Sticky Sessions am Load Balancer (weniger elegant)

Bis dahin reicht eine API-Instanz mit In-Memory-Session.
