# Auth-Alternativen: JWT, Session, Keycloak

Dieses Dokument hält den **Vergleich** fest, der der Entscheidung für **Session ohne Keycloak** vorausging. Die gültige Festlegung steht in [auth-session.md](auth-session.md) und [entscheidungen.md](entscheidungen.md) (ADR-007).

---

## Ausgangslage

Bei **React + Spring Boot** muss das Backend jeden API-Request einem Benutzer zuordnen (außer öffentlichen Endpunkten wie Saldo-per-Token).

Drei Richtungen wurden diskutiert:

1. **JWT** (stateless, Bearer-Token)
2. **Session** (stateful, HttpOnly-Cookie)
3. **Keycloak** (OIDC/OAuth2, typischerweise JWT vom Provider)

---

## JWT (Bearer-Token)

### Vorteile

- Kein gemeinsamer Session-Store nötig → einfache horizontale Skalierung der API.
- Üblich in SPA-Tutorials; klare Trennung von Origins möglich.
- Gleiches Modell für spätere Mobile-Clients.

### Nachteile

- **Logout / Sperre** erst nach Token-Ablauf wirksam, außer mit Blacklist, kurzer TTL oder Refresh-Rotation.
- **XSS:** Token in `localStorage` ist riskant; besser Memory + kurze TTL oder HttpOnly-Cookie auch für JWT.
- Implementierungsaufwand: Access + Refresh, Rotation, Fehlerfälle.
- Größere Requests (Claims in jedem Call).

---

## Session (HttpOnly-Cookie)

### Vorteile

- **Logout sofort** (Session invalidieren).
- Cookie **HttpOnly** → JavaScript liest Session-ID nicht (weniger trivialer Token-Diebstahl als localStorage-JWT).
- Einfaches Modell mit Spring Security.
- Kurze Requests (nur Session-ID).

### Nachteile

- Bei **mehreren API-Instanzen** Session-Store nötig (z. B. Spring Session + Redis) oder Sticky Sessions.
- **CORS/Credentials** in Dev aufwendiger (`localhost:5173` + `8080`) → Vite-Proxy oder SameSite-Konfiguration.
- **CSRF** muss aktiv gelöst werden (Spring-Standard für Cookie-Auth).

---

## Keycloak (OIDC)

### Vorteile

- Fertiger Login, Passwort-Reset, optional 2FA, LDAP/Social später.
- Zentrale Rollen, SSO über mehrere Apps.
- Spring Boot als **OAuth2 Resource Server** (JWT von Keycloak).

### Nachteile

- Zusätzlicher Dienst + eigene DB, Betrieb und Updates.
- Höhere Komplexität (Realm, Client, PKCE, Redirect-URIs).
- Für kleine Vereins-App oft **Overkill**.
- Legacy-Registrierung und `USERS`-Tabelle müssten in Keycloak-Modell überführt werden.
- **Saldo-Link-Token** bleibt ohnehin applikationsspezifisch (nicht Keycloak).

### Entscheidung

**Keycloak wird nicht verwendet.**

---

## JWT vs. Session – Entscheidung für VBB

| Kriterium | Gewinner für VBB |
|-----------|------------------|
| Wenige Nutzer, ein Deployment | **Session** |
| nginx: `/` + `/api` unter einer Domain | **Session** (SameSite, CSRF handhabbar) |
| Sofortiger Logout | **Session** |
| Minimale Infrastruktur | **Session** (kein Keycloak, kein Redis zunächst) |
| Mehrere API-Replicas ohne Sticky | JWT oder Session + Redis (später) |

**Beschlossen:** Session-Cookie mit Spring Security.

---

## Sonderfall: öffentlicher Saldo (Token)

Legacy: `saldo.xhtml` mit gültigem Eintrag in Tabelle `TOKEN` – **ohne** Login.

**Ziel:** eigener REST-Endpunkt, z. B. `GET /api/public/saldo?token=…`, **ohne** Session und **ohne** Keycloak. Separat von der Session-Auth der Hauptanwendung.
