# TaskManager – Vorlesungsprojekt (Termin 4)

**API-Design, Validierung von Benutzereingaben, Fehlermeldungen und Fehlerbehandlung mit Spring Boot**

### Inhalt
- React 18 + Vite + TypeScript
- Komponentenbasierte UI mit React Router
- Wiederverwendbare Komponenten (`TaskList`, `TaskForm`, `Layout`, `Dashboard`)
- Vollständige CRUD-Funktionalität (Erstellen, Lesen, Aktualisieren, Löschen)
- Basic Auth für die REST-API-Kommunikation
- CORS-Konfiguration für die Entwicklung
- Environment-Variablen für sensible Daten
- Unterstützung für beide Build-Tools (Maven und Gradle)
- **Neu in Termin 4:**
    - Deklarative Validierung mit Jakarta Bean Validation (@NotBlank, @Size, @FutureOrPresent, @Pattern)
    - Strukturierte JSON-Fehlermeldungen über `@RestControllerAdvice`
    - Erweiterte DTOs als Java Records (TaskRequest)
    
### Good Practice
- Trennung von Backend und Frontend in eigenen Modulen
- Wiederverwendbarkeit von Komponenten im React-Frontend
- Zentrale Security- und CORS-Konfiguration
- Environment-Variablen für sensible Daten (Basic Auth)
- Klare Schichtentrennung
- Deklarative Validierung direkt im DTO (keine manuelle Prüfung im Controller)
- Globale, einheitliche Fehlerbehandlung für alle Endpunkte

### Wichtig zu wissen
- Das Projekt läuft mit **Spring Boot 4.0.5**, **Java 21**, **React 18** und **Vite**.
- Das Backend startet auf Port **8080**, das Frontend-Dev-Server auf Port **5173**.
- Die REST-API ist mit Basic Auth geschützt (`user`/`password`).
- Es gibt sowohl Maven (`pom.xml`) als auch Gradle (`build.gradle.kts`) Unterstützung.
- Termin 4-Funktionen (Validierung, strukturierte Fehler, Formatierung) sind vollständig implementiert und durch Tests abgesichert.

### Voraussetzungen
- Java 21 (oder höher)
- Node.js 24.15.0 (oder höher) und npm 11.12.1
- IntelliJ IDEA (empfohlen) oder ein anderer IDE
- Internetverbindung (zum ersten Mal für das Herunterladen der Dependencies)

### Anwendung starten

#### Mit Maven

```bash
# 1. Root-Projekt bauen
./mvnw clean install

# 2. Backend starten (in separatem Terminal)
./mvnw -pl backend spring-boot:run 

# 3. Frontend Development-Server starten (in separatem Terminal)
./mvnw -pl frontend frontend:npm@npm-dev
```

#### Mit Gradle

```bash
# 1. Root-Projekt bauen
./gradlew clean build

# 2. Backend starten (in separatem Terminal)
./gradlew :backend:bootRun

# 3. Frontend Development-Server starten (in separatem Terminal)
./gradlew :frontend:frontendDev
```

### Wichtige URLs

- **React Frontend (Development)**: `http://localhost:5173`
- **REST-API**: `http://localhost:8080/api/tasks`
- **Swagger-UI**: `http://localhost:8080/swagger-ui.html`

### Login-Daten (Basic Auth für React-Frontend)

| Benutzer  | Passwort  | Rolle   |
|-----------|-----------|---------|
| `user`    | `password`| USER    |
| `admin`   | `admin`   | ADMIN   |

### Tests ausführen

- Alle Tests: Rechtsklick auf `src/test` → **Run Tests**
- Oder per Maven: `./mvnw clean test`
- Oder per Gradle: `./gradlew test`

### Weitere Hinweise

Alle Klassen und Konfigurationsdateien enthalten ausführliche Kommentare mit **Good Practices**.

Das Projekt ist bewusst so aufgebaut, dass sowohl Maven als auch Gradle parallel verwendet werden können.

Viel Erfolg beim Nachvollziehen und Erweitern der Anwendung!

---

## Lizenz

Dieses Projekt steht unter der [MIT License](LICENSE).