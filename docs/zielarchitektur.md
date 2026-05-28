# Zielarchitektur (vbb-next)

## Überblick

Die Neuentwicklung ersetzt WildFly, JSF und container-managed Security durch eine **SPA** und ein **eingebettetes Spring-Boot-Backend** (fat JAR). Die Fachdomäne (Saisons, Spiele, Spieler, Buchungen, Saldo, Benutzer, Admin) bleibt inhaltlich gleich.

```
┌─────────────────────────────────────────────────────────────────┐
│  Browser                                                         │
└────────────────────────────┬────────────────────────────────────┘
                             │ HTTPS
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│  Reverse Proxy (nginx) – eine Domain empfohlen                   │
│    /          → React (static, Vite build)                       │
│    /api/*     → Spring Boot 4                                    │
└────────────────────────────┬────────────────────────────────────┘
                             │
         ┌───────────────────┴───────────────────┐
         ▼                                       ▼
┌─────────────────┐                   ┌─────────────────┐
│  React (SPA)    │  Session-Cookie   │  Spring Boot 4  │
│  TypeScript     │◄─────────────────►│  REST /api      │
│  Vite           │  credentials:     │  JPA, Security  │
│                 │  include + CSRF   │  Mail, Flyway   │
└─────────────────┘                   └────────┬────────┘
                                               │ JDBC
                                               ▼
                                      ┌─────────────────┐
                                      │  PostgreSQL     │
                                      └─────────────────┘
```

## Technologie-Matrix

| Bereich | Legacy (`master`) | Ziel (`feature/vbb-next`) |
|---------|-------------------|---------------------------|
| Java | 11 | **25** |
| Build | Maven | **Gradle** (Kotlin DSL, 8.14+) |
| Runtime | WildFly / Java EE 8 | **Spring Boot 4** (Jakarta EE 11-Baseline, embedded Tomcat) |
| UI | JSF 2.2, PrimeFaces 7, XHTML | **React**, Vite, TypeScript |
| API | Keine REST-Schicht (Beans ↔ XHTML) | **REST** unter `/api`, OpenAPI (springdoc) |
| DB | MySQL | **PostgreSQL** |
| Auth | FORM-Login, `secureDomainVBB`, View `V_USER_ROLE` | **Spring Security**, Session, Rollen in DB |
| Passwörter | MD5 (JBoss `CryptoUtil`) | **BCrypt** – keine Übernahme alter Hashes |
| E-Mail | Velocity 1.7 | z. B. **Spring Mail** + **Thymeleaf** |
| Schema | `db/createDDL.sql` manuell | **Flyway**-Migrationen |
| Deploy | WAR auf WildFly | **JAR** + Docker Compose (Postgres, API, nginx) |

Spring Boot 4 unterstützt laut [System Requirements](https://docs.spring.io/spring-boot/system-requirements.html) **Java 17 bis 25**.

## Geplantes Repository-Layout

```
vbb_classic/                    # Branch feature/vbb-next
├── docs/                       # Planungsdokumentation
├── backend/                    # Spring Boot 4 (Gradle)
├── frontend/                   # React + Vite (Gradle oder npm)
├── docker-compose.yml
├── db/
│   └── schema/                 # Flyway-Skripte (aus Legacy-DDL abgeleitet)
├── src/                        # Legacy (Referenz bis Cutover)
└── README.md                   # Legacy-Betrieb; Verweis auf docs/
```

## Backend-Schichten (Ziel)

| Paket / Schicht | Aufgabe |
|-----------------|--------|
| `api` | REST-Controller, Request/Response-DTOs |
| `domain` | JPA-Entitäten (portiert aus `de.docfaust.vbb.data.entity`) |
| `repository` | Spring Data `JpaRepository` |
| `service` | Geschäftslogik (aus `service`, `data.facades`) |
| `security` | `UserDetailsService`, Login/Logout, CSRF |
| `mail` | Versand, Queue (`MAILS`) |
| `config` | Security, JPA, CORS (nur falls nötig in Dev) |

EJBs, JSF-Beans, Faces-Converter und -Validatoren entfallen; Logik wandert in Services bzw. Bean Validation an DTOs.

## Frontend (Ziel)

| Thema | Empfehlung |
|-------|------------|
| Tooling | Vite, TypeScript |
| Routing | React Router |
| Server-State | TanStack Query |
| HTTP | Axios oder fetch mit `credentials: 'include'` |
| UI | z. B. MUI oder Ant Design |
| Formulare | React Hook Form + Zod |
| Texte | i18n (z. B. react-i18next); Inhalte aus Legacy-`messages.xml` → `de.json` |

## REST-Bereiche (grob)

Aus dem Legacy-Menü und den XHTML-Bereichen abgeleitet:

| Bereich | Endpunkte (Präfix `/api`) |
|---------|---------------------------|
| Auth | `login`, `logout`, `register`, `confirm` |
| Saisons | CRUD `/seasons` |
| Spiele | CRUD `/games`, Suche |
| Spieler | CRUD `/players` |
| Buchungen | pro Spiel / individuell `/bookings` |
| Saldo | `/saldo` (eingeloggt) |
| Profil | `/profile` |
| Admin | `/admin/users`, `/admin/config`, `/admin/tokens`, Buchungsschnitt |
| Öffentlich | `/public/saldo?token=…` (ohne Session, wie Legacy-`saldo.xhtml`) |

Details und OpenAPI folgen mit der Implementierung.

## Mapping Legacy-UI → React-Routen

| Legacy (JSF unter `/faces/…`) | React-Route (Vorschlag) |
|-------------------------------|-------------------------|
| `index`, `login`, `register` | `/`, `/login`, `/register` |
| `reader/summary` | `/dashboard` |
| `saldo.xhtml` (Token) | `/saldo/:token` |
| `users/spieleingeben`, `searchspiel`, `createIndividualBooking` | `/games`, `/games/new`, … |
| `users/editspieler`, `editsaison` | `/players`, `/seasons` |
| `reader/editProfile` | `/profile` |
| `admin/*` | `/admin/…` |

## Was entfällt

- Gesamtes `src/main/webapp` (XHTML, Faces, `jboss-web.xml`, `web.xml`-Security)
- WildFly CLI (`cli/db.cli`), WildFly-Maven-Plugin
- Container-Security, `V_USER_ROLE`
- PrimeFaces, JSF, `javax.faces.*`
- Velocity 1.7 (durch Thymeleaf o. Ä. ersetzt)
- MD5-Passwort-Hashes

## Was übernommen / portiert wird

- Fachliche Regeln und Entitäten (als Referenz aus `src/main/java`)
- Datenmodell → PostgreSQL-DDL / Flyway `V1__baseline.sql`
- Texte aus `messages.xml`, E-Mail-Vorlagen aus `templates/*.vm` als Vorlage
- Tests im Legacy-Projekt als **Spezifikation** für neue REST-Tests

## Datenbank PostgreSQL

- Relationales Modell bleibt (FKs, Buchungen, Saldo-Summen).
- **MongoDB** wurde verworfen (andere Modellierung, hoher Rewrite-Aufwand, schlechter Fit für Saldo/Joins).
- Migration: DDL aus `db/createDDL.sql` dialect-anpassen (`SERIAL`, Typen, keine MySQL-spezifischen Konstrukte); View `V_USER_ROLE` entfällt.

## Aufwand (grobe Schätzung)

| Szenario | Dauer |
|----------|--------|
| 1 Entwickler Vollzeit | ca. 3–4 Monate |
| Backend + Frontend parallel | ca. 6–10 Wochen |

## Cutover

1. Postgres mit Flyway-Baseline + optional Testdaten
2. Backend + Frontend deployen (Docker Compose)
3. DNS/Proxy von Legacy-`/vbb/faces/…` auf neue App
4. WildFly abschalten; Legacy-Code auf `master` archiviert lassen
