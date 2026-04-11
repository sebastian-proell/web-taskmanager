# TaskManager - Termin 1

**Programmierung von Web-Anwendungen**  
Termin 1 – Objektorientiertes Java-Modell

## Inhalt dieses Branches

In diesem Termin wird das grundlegende objektorientierte Modell für den TaskManager erstellt.  
Es dient als Basis für alle weiteren Termine.

### Enthaltene Klassen

| Klasse                        | Zweck / Lerninhalt                                              | Wichtiges OO-Prinzip                     |
|-------------------------------|------------------------------------------------------------------|------------------------------------------|
| `AbstractEntity.java`         | Basisklasse für alle Entitäten mit ID und Vergleichsmethoden     | Vererbung, Abstraktion                   |
| `TaskStatus.java`             | Enum für den Status einer Aufgabe                                | Aufzählungstypen (Enums)                 |
| `TaskPriority.java`           | Enum für die Priorität einer Aufgabe                             | Aufzählungstypen (Enums)                 |
| `Task.java`                   | Hauptklasse einer Aufgabe mit Attributen und Business-Methoden   | Kapselung, Business-Logik                |
| `User.java`                   | Einfache Benutzerklasse                                          | Vererbung, Kapselung                     |
| `TaskRepository.java`         | Interface für den Datenzugriff auf Tasks                         | Abstraktion, Interface                   |
| `InMemoryTaskRepository.java` | In-Memory-Implementierung des Repositories                       | Polymorphie, Interface-Implementierung   |
| `TaskManager.java`            | Demo zum manuellen Ausprobieren der Klassen                      | Einstiegspunkt der Anwendung                                        |
| `TaskTest.java`               | Automatisierte Unit-Tests für die Task-Klasse                    | Unit-Testing                             |

Diese Klassen zeigen wichtige objektorientierte Prinzipien, die in modernen Web-Anwendungen essenziell sind:
- Vererbung und Abstraktion
- Kapselung von Daten und Verhalten
- Verwendung von Enums zur Vermeidung ungültiger Zustände
- Trennung von Interface und Implementierung (Vorbereitung auf spätere Schichten)

## Ausführen der Demo

```bash
# Mit Maven
./mvnw compile exec:java -Dexec.mainClass="de.sp.taskmanager.TaskManager"

# Mit Gradle (nach Hinzufügen einer Application-Klasse in späteren Terminen)
./gradlew run
```

## Tests ausführen

```bash
# Maven
./mvnw test

# Gradle
./gradlew test
```


## Lizenz

Dieses Projekt steht unter der [MIT License](LICENSE).