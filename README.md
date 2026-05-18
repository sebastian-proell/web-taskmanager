# TaskManager – Vorlesungsprojekt (Termin 6)

**Termin 6** – Moderne Frontend-Architektur mit Komponentenbibliotheken

## Features (Stand Termin 6)

- **Material UI (MUI) Integration** – Professionelles, konsistentes Design mit zentralem Theme
- **Responsives Dashboard** – Übersicht mit dynamischen Statistiken (QuickStats)
- **Vollständige Task-Verwaltung** – Erstellen, Bearbeiten, Löschen von Tasks
- **Sauberer Edit-Flow** – Klick auf „Bearbeiten“ in der Liste → Formular wird automatisch befüllt
- **Trennung von Übersicht und Bearbeitung** – Dashboard vs. TaskManagement-Seite
- **Zentrale State-Verwaltung** – Custom Hooks (`useTasks`, `useTaskForm`) mit `useReducer`
- **Vollständige Validierung** – Client- und Server-seitige Validierung mit klarer Fehleranzeige

Dadurch wird die **Datenbindung** (controlled components + einheitliches formData-Modell) und die **Zustandsverwaltung** (explizite Zustandsmaschinen via useReducer) klar und wartbar umgesetzt.

### Good Practice (Termin 6)

- **Single Source of Truth** – Nur eine `useTasks`-Hook-Instanz pro Feature
- **Controlled Components** – Alle Formularfelder sind kontrolliert
- **Komponentenbibliothek** – Material UI für Konsistenz und Accessibility
- **Responsives Design** – MUI Grid mit Breakpoints
- **Saubere Trennung** – Präsentation (Components) vs. Logik (Hooks)

### Wichtig zu wissen

Durch die konsequente Nutzung einer Komponentenbibliothek wie Material UI konnte die Entwicklungszeit für Standard-UI-Elemente drastisch reduziert werden. 
Gleichzeitig wurde ein einheitliches Design-System etabliert, das Accessibility-Standards erfüllt und bei zukünftigen Erweiterungen konsistent bleibt.

### Projektstruktur (wichtige Dateien Termin 6)

```
web-taskmanager/
├── frontend/                 # React + Vite + TypeScript
│   ├── src/
│   │   ├── components/       # UI-Komponenten (Dashboard, TaskForm, TaskList, QuickStats...)
│   │   ├── hooks/            # Custom Hooks (useTasks, useTaskForm)
│   │   ├── services/         # API-Kommunikation
│   │   └── App.tsx
│   └── package.json
│
├── backend/                  # Spring Boot
│   ├── src/main/java/...
│   └── build.gradle
│
└── README.md
```

## Wichtige Seiten

| Route              | Beschreibung                              |
|--------------------|-------------------------------------------|
| `/`                | Dashboard mit Statistiken und Übersicht   |
| `/tasks`           | Vollständige Task-Verwaltung (Form + Liste) |

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