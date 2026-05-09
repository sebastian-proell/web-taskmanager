# TaskManager – Vorlesungsprojekt (Termin 5)

**Datenbindung und Zustandsverwaltung – Verknüpfung von View und Model**

### Architektur-Übersicht (Termin 5)

Das Projekt wurde in Termin 5 um eine saubere Schichtentrennung erweitert:

- **taskService.ts** — Zentrale API-Kommunikationsschicht
- **useTaskForm Hook** — Formular-Logik + Datenbindung mit `useReducer`
- **useTasks Hook** — Listenverwaltung + CRUD mit `useReducer`
- **TaskForm.tsx** & **TaskList.tsx** — Reine Präsentationskomponenten

Dadurch wird die **Datenbindung** (controlled components + einheitliches formData-Modell) und die **Zustandsverwaltung** (explizite Zustandsmaschinen via useReducer) klar und wartbar umgesetzt.

### Good Practice (Termin 5)

- Trennung von UI und Business-Logik durch Custom Hooks
- Zentrale API-Schicht (`taskService`)
- Verwendung von `useReducer` für konsistente Zustandsübergänge (Formular + Liste)
- Komponenten bleiben schlank und fokussieren sich auf Rendering
- Single Source of Truth für Formular- und Listen-State
- Explizite Props + Callbacks für die Verknüpfung zwischen Komponenten

### Wichtig zu wissen

- Das Projekt verwendet **React 18 + Vite + TypeScript** und **Spring Boot 4**
- Alle sensiblen Daten (Credentials) kommen aus Environment-Variablen
- Der `useTaskForm` Hook implementiert eine eigene kleine Zustandsmaschine für den Submit-Prozess
- Der `useTasks` Hook verwaltet Lade-, Fehler- und Bearbeitungszustände zentral
- Backend und Frontend sind weiterhin komplett getrennt

### Projektstruktur (wichtige Dateien Termin 5)

```
frontend/src/
├── services/
│   └── taskService.ts          # Zentrale API-Schicht
├── hooks/
│   ├── useTaskForm.ts          # Formular-State + Datenbindung (mit useReducer)
│   └── useTasks.ts             # Listen-State + CRUD (mit useReducer)
├── components/
│   ├── TaskForm.tsx            # Reine UI-Komponente (nutzt useTaskForm)
│   ├── TaskList.tsx            # Reine UI-Komponente (nutzt useTasks)
│   ├── TaskFormField.tsx
│   └── ValidationErrorDisplay.tsx
└── App.tsx
```

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