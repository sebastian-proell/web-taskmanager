# TaskManager – Vorlesungsprojekt (Termin 2)

**Grundlegende Spring-Boot REST-API mit JPA, Security und vollständiger Kommentierung**

Dieses Projekt ist das offizielle Begleitprojekt für **Termin 2** des Moduls „Programmierung von Web-Anwendungen – webbasierte betriebliche Informationssysteme“.

### Inhalt dieses Termins
- Spring Boot Grundgerüst mit REST-Controller und CRUD-Endpunkten
- Domain-Model: `Task`-Entity + `TaskStatus`-Enum + DTOs (`TaskRequest` / `TaskResponse`)
- Spring Data JPA Repository + Service-Schicht mit Business-Logik
- SecurityConfiguration mit In-Memory-Benutzern (`user`/`password` und `admin`/`admin`)
- DataInitializer für Beispiel-Daten
- H2 In-Memory-Datenbank + H2-Konsole
- Swagger-UI (springdoc) für interaktive API-Dokumentation
- Ausführliche Vorlesungskommentare in allen Klassen
- Vollständige Test-Suite (Unit-, Integration- und Repository-Tests)

### Good Practice
- Trennung der Schichten (Controller → Service → Repository)
- Verwendung von DTOs zur Entkopplung von API und Datenbank-Modell
- Konstruktor-Injection statt Feld-Injection
- Zentrale Exception-Handling und Security-Konfiguration

### Wichtig zu wissen
- Das Projekt läuft mit **Spring Boot 4.0.5** und **Java 21**.
- Es gibt sowohl eine `build.gradle.kts` als auch eine `pom.xml` (Maven).
- Die Anwendung startet auf Port **8080**.

### Voraussetzungen
- Java 21 (oder höher)
- IntelliJ IDEA (empfohlen) oder ein anderer IDE
- Internetverbindung (zum ersten Mal für das Herunterladen der Dependencies)

### Anwendung starten

1. Projekt in IntelliJ öffnen
2. Rechtsklick auf `TaskManagerApplication.java` → **Run**
3. Die Anwendung startet automatisch auf `http://localhost:8080`

### Wichtige URLs

- **REST-API**: `http://localhost:8080/api/tasks`
- **Swagger-UI** (interaktive Dokumentation): `http://localhost:8080/swagger-ui.html`
- **H2-Datenbank-Konsole**: `http://localhost:8080/h2-console`  
  (JDBC-URL: `jdbc:h2:mem:testdb`, Benutzer: `sa`, Passwort: `password`)

### Login-Daten (Basic Auth)

| Benutzer  | Passwort  | Rolle   |
|-----------|-----------|---------|
| `user`    | `password`| USER    |
| `admin`   | `admin`   | ADMIN   |

### Tests ausführen
- Alle Tests: Rechtsklick auf `src/test` → **Run Tests**
- Oder per Maven: `mvn clean test`
- Oder per Gradle: `./gradlew test`

### Weitere Hinweise
Alle Klassen und Konfigurationsdateien enthalten ausführliche Kommentare mit **Good Practice** und **Wichtig zu wissen** – genau wie in der Vorlesung besprochen.

Viel Erfolg beim Nachvollziehen und Erweitern der Anwendung!

---

*Erstellt für das duale Studium MSD/DMSD an der IU Internationale Hochschule – Termin 2*