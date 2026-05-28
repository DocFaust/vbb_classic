# Architekturentscheidungen

Übersicht der im Planungsgespräch getroffenen Entscheidungen im Stil kurzer **Architecture Decision Records (ADR)**.

---

## ADR-001: Big Bang statt schrittweiser Migration

**Status:** beschlossen

**Kontext:** Die Legacy-App läuft als JSF-WAR auf WildFly. Eine schrittweise Migration (Strangler, JSF + REST parallel) erhöht den Wartungsaufwand.

**Entscheidung:** Kompletter Neubau von Backend und Frontend; ein Cutover. Branch `feature/vbb-next`, `master` bleibt Legacy.

**Konsequenzen:** Ein Release-Zeitpunkt; dafür keine doppelte UI-Logik. Nutzer können neu angelegt werden (kein Migrationsdruck).

---

## ADR-002: Weg von WildFly / Java EE

**Status:** beschlossen

**Entscheidung:** Kein App-Server; **Spring Boot 4** als eingebetteter Server (Tomcat).

**Konsequenzen:** Kein `web.xml`-Security, keine JNDI-Datasource auf dem Server; Konfiguration über Spring Boot + Docker.

---

## ADR-003: SPA mit React

**Status:** beschlossen

**Entscheidung:** Frontend **React** (Vite, TypeScript), keine JSF/PrimeFaces-Oberfläche im Zielsystem.

**Konsequenzen:** REST-API Pflicht; alle bisherigen XHTML-Flows werden neu gebaut.

---

## ADR-004: Java 25 und Spring Boot 4

**Status:** beschlossen

**Entscheidung:** **Java 25**, **Spring Boot 4** (kompatibel mit Java 17–25 laut Spring-Dokumentation).

**Konsequenzen:** Jakarta EE 11 / Servlet 6.1-Baseline; Undertow entfällt; vor Start Release Notes und Migration Guide lesen.

---

## ADR-005: Gradle statt Maven

**Status:** beschlossen

**Entscheidung:** Build-Tool für das neue System: **Gradle** (Kotlin DSL empfohlen, Version 8.14+).

**Konsequenzen:** Neues Build-Layout (`backend/`, `frontend/`); Legacy auf Maven bleibt bis Cutover unberührt.

---

## ADR-006: PostgreSQL statt MySQL / MongoDB

**Status:** beschlossen

**Kontext:** MySQL im Legacy; MongoDB und PostgreSQL wurden diskutiert.

**Entscheidung:** **PostgreSQL** für die Neuentwicklung.

**Begründung:**

- Domäne ist relational (Buchung → Spiel → Saison, Saldo-Aggregationen).
- JPA-Portierung aus Legacy nahezu 1:1.
- MongoDB würde Dokumentmodell und Queries neu erzwingen – hoher Aufwand ohne fachlichen Gewinn.

**Konsequenzen:** DDL-Migration; Flyway; Testcontainers mit Postgres in Tests.

---

## ADR-007: Session-Auth ohne Keycloak

**Status:** beschlossen

**Kontext:** JWT vs. Session-Cookie vs. **Keycloak** (OIDC) wurden verglichen.

**Entscheidung:** **Spring Security** mit **serverseitiger Session** und **HttpOnly-Cookie**. **Kein Keycloak**, **kein JWT** für die Standard-Anmeldung.

**Begründung (Kurz):**

- Weniger Infrastruktur (kein separater Identity-Server).
- Logout und Passwort-Reset einfacher steuerbar.
- Passend für überschaubare Nutzerzahl und Deployment hinter **einem nginx** (gleiche Site für SPA und `/api`).

Details: [auth-session.md](auth-session.md), Vergleich: [auth-alternativen.md](auth-alternativen.md).

**Konsequenzen:** CSRF-Schutz aktiv; React nutzt `credentials: 'include'`; öffentlicher Saldo-Token bleibt separater Endpunkt ohne Session.

---

## ADR-008: Passwörter neu (BCrypt)

**Status:** beschlossen

**Kontext:** Legacy nutzt **MD5** über JBoss `PasswordUtil` / `CryptoUtil`.

**Entscheidung:** Im neuen System nur **BCrypt** (`PasswordEncoder`); alte Hashes werden **nicht** migriert.

**Begründung:** Big Bang ohne Nutzer-Migrationszwang; MD5 ist unsicher.

---

## ADR-009: UTF-8 im neuen Stack

**Status:** empfohlen / voraussichtlich

**Kontext:** Legacy nutzt ISO-8859-1 durchgängig.

**Entscheidung:** Neuentwicklung mit **UTF-8** (Quellcode, DB-Collation, HTTP).

---

## ADR-010: Keine Microservices

**Status:** beschlossen

**Entscheidung:** Ein **Monolith** (API + eine Postgres-DB); kein Aufsplitten in Microservices.

**Begründung:** Domänengröße und Teamgröße rechtfertigen keine verteilte Architektur.

---

## Verworfene Optionen (Kurz)

| Option | Grund der Ablehnung |
|--------|---------------------|
| WildFly / Payara behalten | Ziel ist explizit Weg vom App-Server |
| JSF + React hybrid | Doppelte Wartung |
| MongoDB | Schlechter Fit, hoher Rewrite |
| Keycloak | Zu viel Ops für Vereins-App; Session reicht |
| JWT als Standard-Auth | Bei einer Domain Session einfacher/sicherer (siehe auth-alternativen.md) |
| Schrittweise Jakarta-Migration am Legacy-WAR | Kein Ziel – Big Bang auf Spring Boot 4 |
