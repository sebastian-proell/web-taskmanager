# TaskManager – Vorlesungsprojekt

**Begleitendes Repository für ein Vorlesungsprojekt zur Webprogrammierung**

Dieses Repository enthält den Projektstand aller sechs Vorlesungstermine.  
Jeder Branch bildet den Stand eines einzelnen Termins ab.    
So können Sie die Entwicklung Schritt für Schritt nachvollziehen und das Projekt eigenständig erweitern.

### Technische Basis
- **Group ID**: `de.sp`
- **Artifact ID**: `taskmanager`
- **Java**: 21
- **Spring Boot**: 4.0.5
- **Vaadin**: 25.1.1
- Build-Tools: Maven 3.9.14 **und** Gradle 9.4.1 (beide Wrapper enthalten)

### Branches Übersicht

| Branch       | Termin | Inhalt des Branches                                                                 | Lektion / Fokus                                      |
|--------------|--------|-------------------------------------------------------------------------------------|------------------------------------------------------|
| `termin-1`   | 1      | Reines objektorientiertes Java-Modell (Task, Enums, Business-Methoden)             | Objektorientierte Webentwicklung                     |
| `termin-2`   | 2      | Spring Boot Backend + REST-API + JPA/Hibernate + grundlegende Security             | Grundlagen betrieblicher Web-Anwendungen             |
| `termin-3`   | 3      | Komponentenbasierte UI mit Vaadin (Grid, Form, Layout)                             | Komponentenbasierte Web-Benutzerschnittstellen       |
| `termin-4`   | 4      | Validierung, Fehlerbehandlung, Formatierung (Datum, Zahlen, Währung)               | Programmierung von Geschäftslogik                    |
| `termin-5`   | 5      | Datenbindung und Zustandsverwaltung (View-Model-Verknüpfung)                       | Verknüpfung von View und Model                       |
| `termin-6`   | 6      | Vollständige Anwendung mit Komponentenbibliothek, Web-Services und Production-Ready | Entwicklung mit Komponentenbibliotheken und Spring Boot |
| `main`       | -      | Finale Version nach Termin 6                                                        | Gesamte Anwendung                                    |


### So wechseln Sie zum gewünschten Termin

```bash
# Zum jeweiligen Vorlesungstermin wechseln
git checkout termin-1        # nach Termin 1
git checkout termin-2        # nach Termin 2
git checkout termin-3        # usw.
```


### Anwendung starten (ab Termin 2)

```bash
# Mit Maven
./mvnw spring-boot:run

# Mit Gradle
./gradlew bootRun
```