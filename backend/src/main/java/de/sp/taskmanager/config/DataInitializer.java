package de.sp.taskmanager.config;

import de.sp.taskmanager.model.Task;
import de.sp.taskmanager.model.TaskStatus;
import de.sp.taskmanager.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Diese Klasse ist verantwortlich für die Initialisierung von Beispiel-Daten in der Anwendung.
 * Sie implementiert das Interface CommandLineRunner, was bedeutet, dass die Methode run() automatisch
 * beim Start der Spring Boot-Anwendung ausgeführt wird. Das ist eine gute Praxis, um Testdaten oder
 * Initialdaten zu laden, ohne dass etwas manuell getan werden muss.
 *
 * Good Practice: Dependency Injection (hier über den Konstruktor) wird verwendet, um Abhängigkeiten wie
 * das Repository zu injizieren. Das macht den Code testbar und flexibel. Statische Methoden
 * oder globale Variablen werden vermieden, da diese den Code schwer wartbar machen.
 *
 * Wichtig zu wissen: Diese Klasse ist ein "Bean" in Spring (durch @Component markiert), was bedeutet, dass
 * Spring sie automatisch erkennt und verwaltet. Klassen werden immer in passende Packages organisiert
 * (hier: config), um den Code übersichtlich zu halten.
 */
@Component
public class DataInitializer {

    private final TaskRepository taskRepository;

    /**
     * Konstruktor der Klasse. Hier wird das TaskRepository injiziert.
     *
     * Good Practice: Konstruktor-Injection ist sicherer als Feld-Injection, da sie die Abhängigkeiten
     * explizit macht und leichter zu testen ist. Es wird sichergestellt, dass alle Abhängigkeiten final sind,
     * um versehentliche Änderungen zu vermeiden.
     *
     * Wichtig zu wissen: Dependency Injection bedeutet, dass Spring das Repository-Objekt automatisch
     * erstellt und hier übergibt. Es ist nicht notwendig, es selbst mit 'new' zu erzeugen – das übernimmt das Framework.
     */
    public DataInitializer(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Diese Methode wird beim Start der Anwendung ausgeführt und erstellt ein Beispiel-Task.
     * Sie speichert das Task im Repository (z.B. einer Datenbank).
     *
     * Good Practice: Methoden werden kurz und fokussiert gehalten (Single Responsibility Principle). Hier wird
     * nur ein Task erstellt – bei Bedarf wird es modular erweitert. Enums wie TaskStatus
     * werden für konstante Werte verwendet, um Tippfehler zu vermeiden.
     *
     * Wichtig zu wissen: LocalDateTime ist eine Klasse aus Java, die Datums- und Uhrzeit-Informationen handhabt.
     * plusDays(7) addiert 7 Tage zum aktuellen Datum. Das Repository ist wie eine "Datenbank-Schnittstelle",
     * die das Speichern übernimmt.
     */
    @PostConstruct
    public void init() {
        if (taskRepository.count() > 0) {
            return; // Datenbank bereits befüllt
        }

        List<Task> tasks = List.of(
                // === OPEN (7 Tasks) ===
                createTask("Projektplanung Q3 finalisieren", "Erstellung des Projektplans für das dritte Quartal inklusive Meilensteine und Ressourcenplanung.", TaskStatus.OPEN, LocalDateTime.of(2026, 6, 15, 10, 0), "Max Mustermann"),
                createTask("API-Dokumentation aktualisieren", "Swagger-Dokumentation der neuen Endpunkte ergänzen und veraltete Beispiele entfernen.", TaskStatus.OPEN, LocalDateTime.of(2026, 5, 28, 14, 30), "Anna Schmidt"),
                createTask("Security-Review durchführen", "Durchführung eines Security-Reviews der aktuellen Webanwendung inklusive OWASP-Checks.", TaskStatus.OPEN, LocalDateTime.of(2026, 6, 10, 9, 0), "Leon Berger"),
                createTask("Neue Landingpage konzipieren", "Konzeption und Wireframing einer überarbeiteten Landingpage für das Kundenportal.", TaskStatus.OPEN, LocalDateTime.of(2026, 6, 20, 11, 0), "Sophie Klein"),
                createTask("Datenbank-Performance optimieren", "Analyse und Optimierung langsamer Queries in der Task-Tabelle.", TaskStatus.OPEN, LocalDateTime.of(2026, 6, 5, 16, 0), "Jonas Weber"),
                createTask("Onboarding-Prozess für neue Mitarbeiter", "Erstellung eines strukturierten Onboarding-Prozesses inklusive Checklisten.", TaskStatus.OPEN, LocalDateTime.of(2026, 6, 30, 8, 0), "Laura Fischer"),
                createTask("Bug: Doppelte Benachrichtigungen", "Untersuchung und Behebung des Bugs mit doppelten E-Mail-Benachrichtigungen.", TaskStatus.OPEN, LocalDateTime.of(2026, 5, 25, 13, 45), "Tim Hoffmann"),

                // === IN_PROGRESS (4 Tasks) ===
                createTask("Migration auf Spring Boot 3.3", "Durchführung der Migration der bestehenden Anwendung auf Spring Boot 3.3.", TaskStatus.IN_PROGRESS, LocalDateTime.of(2026, 5, 22, 10, 0), "Max Mustermann"),
                createTask("Frontend-Testing mit Vitest aufbauen", "Einrichtung von Komponententests und Integrationstests mit Vitest und React Testing Library.", TaskStatus.IN_PROGRESS, LocalDateTime.of(2026, 6, 8, 14, 0), "Anna Schmidt"),
                createTask("Dashboard mit echten Daten verbinden", "Anbindung des Dashboards an das Backend inklusive dynamischer Statistikberechnung.", TaskStatus.IN_PROGRESS, LocalDateTime.of(2026, 5, 20, 9, 30), "Leon Berger"),
                createTask("Barrierefreiheit (a11y) verbessern", "Umsetzung von Accessibility-Verbesserungen gemäß WCAG 2.2.", TaskStatus.IN_PROGRESS, LocalDateTime.of(2026, 6, 12, 11, 0), "Sophie Klein"),

                // === COMPLETED (12 Tasks) ===
                createTask("Login mit JWT implementieren", "Implementierung einer tokenbasierten Authentifizierung mit JWT.", TaskStatus.COMPLETED, LocalDateTime.of(2026, 4, 10, 10, 0), "Max Mustermann"),
                createTask("CRUD-Operationen für Tasks erstellen", "Erstellung der vollständigen CRUD-Funktionalität für Tasks im Backend.", TaskStatus.COMPLETED, LocalDateTime.of(2026, 4, 15, 14, 0), "Anna Schmidt"),
                createTask("Material UI ins Frontend integrieren", "Integration von Material UI inklusive ThemeProvider und grundlegender Komponenten.", TaskStatus.COMPLETED, LocalDateTime.of(2026, 5, 5, 9, 0), "Leon Berger"),
                createTask("Responsive Layout für TaskManagement", "Umsetzung eines responsiven Layouts (nebeneinander → untereinander).", TaskStatus.COMPLETED, LocalDateTime.of(2026, 5, 8, 16, 30), "Sophie Klein"),
                createTask("useTasks Hook erstellen", "Erstellung eines zentralen Custom Hooks für Task-State-Management.", TaskStatus.COMPLETED, LocalDateTime.of(2026, 4, 28, 11, 0), "Jonas Weber"),
                createTask("Error Handling im Frontend verbessern", "Zentrale Fehlerbehandlung und Anzeige von Validierungsfehlern.", TaskStatus.COMPLETED, LocalDateTime.of(2026, 5, 1, 10, 0), "Laura Fischer"),
                createTask("Swagger UI einrichten", "Einrichtung und Konfiguration von Swagger UI für die API-Dokumentation.", TaskStatus.COMPLETED, LocalDateTime.of(2026, 4, 20, 13, 0), "Tim Hoffmann"),
                createTask("Datenbank-Modell finalisieren", "Finalisierung des Task-Entity-Modells und der entsprechenden Repositorys.", TaskStatus.COMPLETED, LocalDateTime.of(2026, 4, 5, 9, 0), "Max Mustermann"),
                createTask("GitHub Actions für CI einrichten", "Einrichtung eines einfachen CI-Pipelines mit Build und Tests.", TaskStatus.COMPLETED, LocalDateTime.of(2026, 4, 25, 15, 0), "Anna Schmidt"),
                createTask("Readme mit Setup-Anleitung schreiben", "Erstellung einer ausführlichen Readme-Datei mit Installations- und Startanleitung.", TaskStatus.COMPLETED, LocalDateTime.of(2026, 5, 3, 10, 0), "Leon Berger"),
                createTask("TypeScript Strict Mode aktivieren", "Aktivierung von strict TypeScript-Einstellungen und Behebung aller Fehler.", TaskStatus.COMPLETED, LocalDateTime.of(2026, 5, 10, 14, 0), "Sophie Klein"),
                createTask("Komponententrennung TaskForm / TaskList", "Saubere Trennung von Formular und Liste in eigenständige Komponenten.", TaskStatus.COMPLETED, LocalDateTime.of(2026, 5, 12, 9, 30), "Jonas Weber"),

                // === BLOCKED (2 Tasks) ===
                createTask("SSO-Integration mit Keycloak", "Anbindung der Anwendung an Keycloak für Single Sign-On. Blockiert durch fehlende Keycloak-Instanz.", TaskStatus.BLOCKED, LocalDateTime.of(2026, 7, 1, 10, 0), "Max Mustermann"),
                createTask("Push-Benachrichtigungen implementieren", "Implementierung von Push-Benachrichtigungen via WebSockets. Blockiert durch fehlende Infrastruktur.", TaskStatus.BLOCKED, LocalDateTime.of(2026, 6, 25, 11, 0), "Anna Schmidt")
        );

        taskRepository.saveAll(tasks);
        System.out.println(">>> " + tasks.size() + " Beispieldaten wurden erfolgreich in die Datenbank geladen.");
    }

    private Task createTask(String title, String description, TaskStatus status, LocalDateTime dueDate, String assignedTo) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setDueDate(dueDate);
        task.setAssignedTo(assignedTo);
        return task;
    }
}

