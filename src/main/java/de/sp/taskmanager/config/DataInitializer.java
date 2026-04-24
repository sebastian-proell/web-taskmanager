package de.sp.taskmanager.config;

import de.sp.taskmanager.model.Task;
import de.sp.taskmanager.model.TaskStatus;
import de.sp.taskmanager.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

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
public class DataInitializer implements CommandLineRunner {

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
    @Override
    public void run(String... args) {
        Task task1 = new Task();
        task1.setTitle("Erste Aufgabe");
        task1.setDescription("Beispiel für Termin 2");
        task1.setStatus(TaskStatus.OPEN);
        task1.setDueDate(LocalDateTime.now().plusDays(7));
        task1.setAssignedTo("user");

        taskRepository.save(task1);
    }
}
