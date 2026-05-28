# VBB – Dokumentation zur Neuentwicklung

Dieses Verzeichnis fasst die **Planungsgespräche** für die Neuentwicklung von Volleyballbuchung (VBB) zusammen. Es beschreibt Ziele, getroffene Entscheidungen und das Zielbild – **ohne** bereits implementierten Code im neuen Stack.

## Ausgangslage

Die bestehende Anwendung (`master`) ist eine **Java-EE-WAR** auf **WildFly** mit **JSF/PrimeFaces**, **JPA/Hibernate** und **MySQL**. Siehe [README.md](../README.md) im Projektroot für Betrieb und Aufbau des Legacy-Systems.

## Vorgehen

| Aspekt | Festlegung |
|--------|------------|
| Strategie | **Big Bang** – kein schrittweises Strangler-Muster auf JSF-Ebene |
| Nutzer-Migration | **Kein** besonderer Bedarf – Bestand kann neu aufgebaut werden (z. B. Passwörter neu, keine MD5-Übernahme) |
| Git-Branch | **`feature/vbb-next`** – gesamte Neuentwicklung auf diesem Branch; `master` bleibt Legacy-Referenz |

## Dokumente

| Datei | Inhalt |
|-------|--------|
| [zielarchitektur.md](zielarchitektur.md) | Ziel-Stack, Schichten, Deployment, Grob-Routen-Mapping |
| [entscheidungen.md](entscheidungen.md) | Architekturentscheidungen (ADR-Stil) mit Begründung |
| [auth-session.md](auth-session.md) | Session-basierte Anmeldung ohne Keycloak (React + Spring Boot) |
| [auth-alternativen.md](auth-alternativen.md) | JWT vs. Session vs. Keycloak – Vergleich und warum nicht gewählt |

## Ziel-Stack (Kurzüberblick)

| Schicht | Technologie |
|---------|-------------|
| Java | **25** |
| Build | **Gradle** (Kotlin DSL) |
| Backend | **Spring Boot 4** (u. a. offiziell Java 17–25) |
| Frontend | **React** (Vite, TypeScript) |
| Datenbank | **PostgreSQL** (relational; kein MongoDB) |
| Auth | **Spring Security**, **Session-Cookie** (kein Keycloak, kein JWT) |
| Schema | **Flyway** (Ausgang aus Legacy-`createDDL.sql`, angepasst an Postgres) |

## Nächste Schritte (geplant)

1. Gradle-Monorepo (`backend/`, `frontend/`)
2. Spring-Boot-Skeleton (JPA, Postgres, Security, Session-Login)
3. React-Skeleton (Vite-Proxy, `credentials: 'include'`)
4. `docker-compose.yml` (Postgres, API, nginx)
5. OpenAPI / REST-Portierung der Fachdomäne

---

*Stand: Planung auf Branch `feature/vbb-next` – vor Beginn der Implementierung.*
