# Pure JSF TaskManager – Termin 5 Vergleich

Diese Version ist eine **reine JSF-Implementierung** (ohne Spring Boot) zur Demonstration von **serverseitiger Datenbindung**.
Aus Gründen der Einfachheit wird in diesem Projekt nur Maven als Build-Tool unterstützt.

## Ziel dieses Projekts

Dieses Projekt dient dem direkten Vergleich mit der React-Variante (Termin 5) hinsichtlich:

- **Datenbindung**: Server-seitig (JSF) vs. Client-seitig (React)
- **Zustandsverwaltung**: Backing Bean + ViewState vs. React State + Hooks
- **Architektur**: Klassische JSF-Schichten vs. moderne React + Service Layer

## Wichtige Dateien und ihre Bedeutung

| Datei                        | Bedeutung für den Unterricht |
|-----------------------------|------------------------------|
| `TaskBean.java`             | **Zentrale Backing Bean** – hier findet die serverseitige Datenbindung statt |
| `tasks.xhtml`               | JSF-Seite mit direkter Wertbindung an Bean-Eigenschaften |
| `TaskService.java`          | Fachliche Logik (In-Memory) |
| `Task.java`                 | Einfaches Modell |

## Good Practice in dieser Variante

- Klare Trennung zwischen Präsentationslogik (`TaskBean`) und Fachlogik (`TaskService`)
- Verwendung von CDI (`@Named`, `@Inject`)
- Explizite Methoden für CRUD-Operationen
- Session-Scoped Bean für langlebigen View-State

## Wichtig zu wissen

Im Gegensatz zur React-Version wird der gesamte Formular- und Listen-State **serverseitig** in der `TaskBean` gehalten. JSF übernimmt automatisch die Synchronisation zwischen XHTML-Komponenten und Bean-Eigenschaften über den JSF-Lifecycle.

## Starten

```bash
./mvnw clean package
```
Die erzeugte WAR-Datei kann auf Payara, GlassFish oder WildFly deployed werden,

oder mit dem integrierten Payara Micro Maven-Plugin gestartet werden, für eine schnelle und direkte Entwicklungsumgebung:

```bash
./mvnw clean package payara-micro:start
```


Die Anwendung ist dann unter http://localhost:8080/taskmanager-jsf/ erreichbar.

## Lizenz

Dieses Projekt steht unter der [MIT License](LICENSE).
