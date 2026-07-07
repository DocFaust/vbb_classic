# Volleyballbuchung (VBB)

Webanwendung zur Verwaltung und Abrechnung der Kosten einer Volleyballgruppe: Spiele erfassen, Teilnehmer buchen, Saisons verwalten und Salden auswerten.

| | |
|---|---|
| **Artefakt** | `de.docfaust:vbb:2.0-SNAPSHOT` (WAR) |
| **Kontextpfad** | `/vbb` |
| **Java** | Build-Baseline JDK 25; Bytecode-Target bleibt 11 (WildFly-Kompatibilitaet) |
| **Zeichenkodierung** | ISO-8859-1 |

## Funktionen

- **Spiele & Buchungen** – Spiele anlegen/bearbeiten, Teilnehmer pro Spiel buchen, individuelle Buchungen
- **Saisons** – Zeiträume mit Preisen; Spiele sind einer Saison zugeordnet
- **Spieler** – Stammdaten und Aktivitätslevel
- **Saldo** – Übersicht pro Spieler und Gesamtsumme (öffentlich per Token-Link oder eingeloggt)
- **Benutzer & Rollen** – Registrierung per E-Mail, Form-Login, Rollen `ADMIN`, `USER`, `READER`
- **Administration** – Benutzerverwaltung, Buchungsschnitt, E-Mail-Konfiguration, Zugangs-Tokens
- **Journal & Mail-Queue** – Protokollierung von Geschäftsvorfällen; ausstehende Mails in `MAILS`

## Technologie-Stack

| Bereich | Technologie |
|---------|-------------|
| Laufzeit | Java EE 8 (WildFly/JBoss) |
| UI | JSF 2.2, Facelets, PrimeFaces 7 |
| Persistenz | JPA/Hibernate 5.4, MySQL |
| DI/CDI | CDI (`beans.xml`, `@Inject`, `@Named`) |
| Build | Maven 3 |
| Tests | JUnit 5, HSQLDB, DBUnit, Mockito, AssertJ |
| Logging | Log4j 2 (YAML-Konfiguration) |
| Vorlagen | Apache Velocity (Registrierungs-Mails) |
| UI-Texte (Fachlich) | XML-Nachrichtenkatalog (`messages.xml`) |
| Qualität | Checkstyle, SpotBugs, JaCoCo (Jenkins-Pipeline) |

## Architektur (Überblick)

```
Browser (JSF/PrimeFaces)
        │
        ▼
WildFly  ── secureDomainVBB (FORM-Login, Rollen)
        │
        ├── JSF Managed Beans (jsfbeans)
        ├── Services (service) ──► Facades (data/facades)
        └── JPA (Persistence Unit „vbb“) ──► MySQL (JNDI: java:/jdbc/vbb2)
```

**Schichten im Quellcode** (`src/main/java/de/docfaust/vbb/`):

| Paket | Inhalt |
|-------|--------|
| `data/entity` | JPA-Entitäten (`Buchung`, `Spiel`, `Spieler`, `Season`, `User`, …) |
| `data/facades` | Stateless EJBs für Datenzugriff |
| `data/factories` | Erzeugung/Validierung von Entitäten |
| `service` | Geschäftslogik (Saldo, Registrierung, Mail, Config, …) |
| `jsfbeans` | View-Controller für XHTML-Seiten |
| `servlet` | REST-artige Endpunkte (z. B. `/register`) |
| `util` | Logging, Journal, Nachrichten, Velocity-Templates |
| `validation` | Validatoren für Saisons und Formulare |

**Web-Oberfläche** (`src/main/webapp/`):

- Einstieg: `faces/index.xhtml`
- Bereiche: `users/` (Spiel, Spieler, Saison), `reader/` (Profil, Saldo-Übersicht), `admin/`, `saldo.xhtml` (Token-Zugang)
- Sicherheit: `WEB-INF/web.xml` (URL-Muster und Rollen), `WEB-INF/jboss-web.xml` (Kontext, Security-Domain)

## Projektstruktur

```
vbb_classic/
├── pom.xml                 # Maven-Build
├── db/
│   ├── createDDL.sql       # MySQL-Schema
│   └── testdata.sql        # Beispieldaten
├── cli/
│   └── db.cli              # WildFly CLI: JDBC-Datasource anlegen
├── src/main/
│   ├── java/               # Anwendungslogik
│   ├── resources/          # persistence.xml, messages.xml, Templates, Log4j
│   └── webapp/             # XHTML, CSS, WEB-INF
├── src/test/java/          # Unit- und Integrationstests
├── jenkins/Jenkinsfile     # CI-Pipeline (Build, JaCoCo, Analyse, Site)
└── .github/workflows/      # GitHub Actions (mvn package)
```

## Voraussetzungen

- **JDK 25** fuer Build/CI (Maven kompiliert weiterhin mit `--release 11`)
- **Maven 3.6+**
- **MySQL** (Datenbank `vbb`)
- **WildFly** (oder kompatibler JBoss EAP) mit konfigurierter Security-Domain `secureDomainVBB` und MySQL-Treiber

## Datenbank einrichten

1. MySQL-Datenbank anlegen:

```sql
CREATE DATABASE vbb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Schema und optional Testdaten laden:

```bash
mysql -u <user> -p vbb < db/createDDL.sql
mysql -u <user> -p vbb < db/testdata.sql   # optional
```

Das Skript legt u. a. Tabellen `SEASON`, `SPIEL`, `SPIELER`, `BUCHUNG`, `USERS`, `ROLES`, `CONFIG`, `MAILS`, `JOURNAL`, `TOKEN` sowie die View `V_USER_ROLE` für die JBoss-Security-Integration an.

## Application Server konfigurieren

### JDBC-Datasource (WildFly CLI)

Die Datei `cli/db.cli` zeigt ein Beispiel für den JNDI-Namen `java:/jdbc/vbb2` (wie in `persistence.xml`):

- MySQL-Treiber als Modul bereitstellen
- Datasource `MySQLPool` mit `connection-url`, Benutzer und Passwort setzen

Vor dem Ausführen `dbuser`, `dbpassword`, `driverpath` und `dburl` an die lokale Umgebung anpassen, dann:

```bash
$WILDFLY_HOME/bin/jboss-cli.sh --file=cli/db.cli
```

### Deployment

- **Kontext:** `/vbb` (`jboss-web.xml`)
- **Security-Domain:** `secureDomainVBB` (muss serverseitig an die Datenbank-View `V_USER_ROLE` angebunden sein)
- WAR bauen und deployen:

```bash
mvn clean package
# Optional mit WildFly-Maven-Plugin (Zugangsdaten per Umgebung setzen):
#   WILDFLY_MGMT_USER
#   WILDFLY_MGMT_PASSWORD
mvn wildfly:deploy
```

Nach dem Start ist die Anwendung typischerweise unter `http://localhost:8080/vbb/faces/index.xhtml` erreichbar.

## Build & Tests

```bash
# Kompilieren und WAR erzeugen
mvn clean package

# Nur Tests
mvn test

# Site-Berichte (Checkstyle, SpotBugs, …) – wie in Jenkins
mvn site
```

Tests nutzen primär **HSQLDB** im Speicher; einige Tests arbeiten mit DBUnit und XML-Fixtures unter `src/test/resources/`.

## Docker Compose (PR2 Grundgeruest)

Ein erstes Compose-Setup fuer **WildFly + MariaDB + Flyway** ist enthalten.

```bash
# 1) Lokale Umgebungswerte vorbereiten
cp .env.example .env

# 2) Stack starten
docker compose up --build
```

Enthaltene Services:

- `mariadb` (persistente Daten in `mariadb-data`)
- `flyway` (fuehrt Migrationen aus `db/migrations/` aus)
- `app` (WildFly mit Deployment der WAR aus `Dockerfile.app`)

### SMTP / Netcup Mail

Der WildFly-Container kann die JavaMail-Session `java:/mail/DocFaust` automatisch aus Umgebungsvariablen anlegen.

Beispiel fuer Netcup in `.env`:

```env
SMTP_HOST=mail.example.netcup.net
SMTP_PORT=465
SMTP_USERNAME=mailbox@example.com
SMTP_PASSWORD=<app-oder-postfach-passwort>
SMTP_FROM=mailbox@example.com
SMTP_SSL=false
SMTP_TLS=true
```

Bei TLS-Fehlern wie `PKIX path building failed` (Zertifikatskette nicht vertrauenswuerdig), optional:

```env
SMTP_SSL_TRUST=mail.example.netcup.net
SMTP_SSL_CHECKSERVERIDENTITY=false
```

Diese beiden Variablen sind ein Workaround fuer fehlerhafte oder unvollstaendige Zertifikatsketten auf SMTP-Seite.

Danach den Stack neu bauen:

```bash
docker compose up --build -d
```

Bei einer frischen Datenbank legt Flyway automatisch einen initialen Admin an:

- Benutzer: `admin`
- Passwort: `admin`

Dieser Bootstrap-Account wird nur angelegt, wenn noch kein Benutzer mit Rolle `ADMIN` existiert.
Bitte Passwort nach dem ersten Login sofort aendern.

Zusätzlich muessen in der Anwendungskonfiguration bzw. Tabelle `CONFIG` diese Werte sinnvoll gesetzt sein:

- `sender.address` = Absenderadresse
- `subject` = Betreff fuer Registrierungs-Mails
- `domain` = oeffentliche Basis-URL, z. B. `http://localhost:8080/vbb`

Hinweis: Dieses Grundgeruest bereitet die naechsten Modernisierungsschritte vor. Die fachliche Vollmigration der bestehenden SQL-Skripte in Flyway erfolgt in der naechsten Phase.

## Sicherheit & Rollen

| Rolle | Zugriff (Auszug) |
|-------|------------------|
| `READER` | `/faces/reader/*` |
| `USER` | zusätzlich `/faces/users/*`, `/faces/m/*` |
| `ADMIN` | zusätzlich `/faces/admin/*` |

Login erfolgt per **FORM-Authentifizierung** (`login.xhtml`). Neue Benutzer registrieren sich über `register.xhtml`; die Bestätigung läuft über `RegisterServlet` (`/register`) und Velocity-E-Mail-Templates in `src/main/resources/templates/`.

## Konfiguration

- **Persistenz:** `src/main/resources/META-INF/persistence.xml` – Dialect MySQL, JNDI `java:/jdbc/vbb2`
- **Laufzeitparameter:** Tabelle `CONFIG` (z. B. Domain-URL für Registrierungslinks)
- **Fachliche Meldungen:** `src/main/resources/messages.xml` (JSF-Growls und Validierung)
- **Logging:** `src/main/resources/log4j2.yml`

## Continuous Integration

- **GitHub Actions** (`.github/workflows/maven.yml`): `mvn package` bei Push/PR auf `master`
- **Jenkins** (`jenkins/Jenkinsfile`): Build, JaCoCo, Checkstyle/PMD/CPD/FindBugs, Maven Site inkl. SpotBugs

Historische CI-URL: https://jenkins.docfaust.de (siehe `pom.xml`).

## Hinweise zur Entwicklung

- **Lombok** wird im Build per `delombok` verarbeitet; IDE-Annotation Processing kann hilfreich sein.
- **PrimeFaces-Repository** in `pom.xml` (`repository.primefaces.org`) – bei Build-Problemen Netzwerk/Proxy prüfen.
- Die Codebasis verwendet durchgängig **ISO-8859-1**; Dateien und Editor entsprechend einstellen.
- Öffentliche Saldo-Ansicht: `saldo.xhtml` mit gültigem Token (siehe `Token`-Entität und `SaldoBean`).

## Lizenz

Keine Lizenzdatei im Repository hinterlegt. Nutzung und Weitergabe nur mit Zustimmung der Rechteinhaber.
