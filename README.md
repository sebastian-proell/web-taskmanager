# TaskManager – Vorlesungsprojekt (Termin 3)

**Komponentenbasierte Web-Benutzerschnittstelle mit Vaadin 25 + Spring Boot**

Dieses Projekt ist das offizielle Begleitprojekt für **Termin 3** des Moduls „Programmierung von Web-Anwendungen – webbasierte betriebliche Informationssysteme“.

### Inhalt dieses Termins
- Komponentenbasierte UI mit Vaadin 25 (AppLayout, Grid, FormLayout, Binder)
- Wiederverwendbare `TaskForm`-Komponente
- `MainLayout` mit moderner Drawer-Navigation und Icons
- `TaskView` mit Grid, CRUD-Funktionalität und Edit-Modus
- `DashboardView` als Startseite
- Vollständige Security-Integration mit eigener `LoginView`
- Vaadin + Spring Security Best Practices (serverseitige Entwicklung)
- Ausführliche Vorlesungskommentare in allen Klassen
- Saubere Trennung von UI-Komponenten und Business-Logik (TaskService aus Termin 2)

### Good Practice
- Wiederverwendbarkeit von Komponenten (`TaskForm`)
- Serverseitige UI-Entwicklung mit Vaadin (kein manueller REST-Call nötig)
- Konstruktor-Injection und klare Schichtentrennung
- Zentrale Security-Konfiguration mit VaadinSecurityConfigurer
- Moderne Navigation mit `AppLayout` und Icons

### Wichtig zu wissen
- Das Projekt läuft mit **Spring Boot 4.0.5**, **Vaadin 25** und **Java 21**.
- Die gesamte Benutzeroberfläche wird serverseitig in Java erstellt.
- Die REST-API aus Termin 2 bleibt vollständig erhalten und wird von Vaadin genutzt.

### Voraussetzungen
- Java 21 (oder höher)
- IntelliJ IDEA (empfohlen) oder ein anderer IDE
- Internetverbindung (zum ersten Mal für das Herunterladen der Dependencies)

### Anwendung starten

1. Projekt in IntelliJ öffnen
2. Rechtsklick auf `TaskManagerApplication.java` → **Run**
3. Die Anwendung startet automatisch auf `http://localhost:8080`

### Wichtige URLs

- **Hauptanwendung (Vaadin UI)**: `http://localhost:8080`
- **Login-Seite**: `http://localhost:8080/login`
- **REST-API** (aus Termin 2): `http://localhost:8080/api/tasks`
- **Swagger-UI**: `http://localhost:8080/swagger-ui.html`
- **H2-Datenbank-Konsole**: `http://localhost:8080/h2-console`

### Login-Daten

| Benutzer  | Passwort  | Rolle   |
|-----------|-----------|---------|
| `user`    | `password`| USER    |
| `admin`   | `admin`   | ADMIN   |

### Tests ausführen
- Alle Tests: Rechtsklick auf `src/test` → **Run Tests**
- Oder per Gradle: `./gradlew test`

### Weitere Hinweise
Alle Klassen und Konfigurationsdateien enthalten ausführliche Kommentare mit **Good Practice** und **Für Anfänger** – genau wie in der Vorlesung besprochen.

Viel Erfolg beim Nachvollziehen und Erweitern der komponentenbasierten Vaadin-Oberfläche!

---

## Lizenz

Dieses Projekt steht unter der [MIT License](LICENSE).