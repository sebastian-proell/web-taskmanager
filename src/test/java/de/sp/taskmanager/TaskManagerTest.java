package de.sp.taskmanager;

import de.sp.taskmanager.model.*;
import de.sp.taskmanager.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Integrationstest für die TaskManager-Demo und die Zusammenarbeit der Klassen.
 *
 * Dieser Test prüft, ob die Model-Klassen, das Repository und die Business-Logik
 * zusammen richtig funktionieren.
 */
class TaskManagerTest {

    private TaskRepository repository;
    private Task task1;
    private Task task2;
    private User user;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTaskRepository();

        task1 = new Task(
                "Login-Funktion implementieren",
                "OAuth2 Integration mit Azure AD",
                TaskPriority.HIGH,
                LocalDate.of(2026, 4, 20),
                "Max Mustermann"
        );

        task2 = new Task(
                "Datenbank optimieren",
                "Indizes auf Task-Tabelle anlegen",
                TaskPriority.MEDIUM,
                LocalDate.of(2026, 4, 10),
                "Anna Schmidt"
        );

        user = new User("Max Mustermann", "max.mustermann@example.com");
    }

    @Test
    @DisplayName("TaskManager sollte mehrere Tasks verwalten können")
    void shouldManageMultipleTasks() {
        // Tasks speichern
        repository.save(task1);
        repository.save(task2);

        // Alle Tasks abrufen
        List<Task> allTasks = repository.findAll();

        assertEquals(2, allTasks.size());
        assertTrue(allTasks.contains(task1));
        assertTrue(allTasks.contains(task2));
    }

    @Test
    @DisplayName("Überfällige Tasks sollten korrekt erkannt werden")
    void shouldDetectOverdueTasks() {
        // task2 auf ein vergangenes Datum setzen
        task2.setDueDate(LocalDate.of(2025, 12, 1));

        repository.save(task1);
        repository.save(task2);

        List<Task> allTasks = repository.findAll();

        long overdueCount = allTasks.stream()
                .filter(Task::isOverdue)
                .count();

        assertEquals(1, overdueCount);
    }

    @Test
    @DisplayName("Markieren einer Aufgabe als erledigt sollte funktionieren")
    void shouldMarkTaskAsDone() {
        repository.save(task1);
        task1.markAsDone();

        Task found = repository.findById(task1.getId()).orElse(null);
        assertNotNull(found);
        assertEquals(TaskStatus.DONE, found.getStatus());
    }

    @Test
    @DisplayName("User und Task sollten gemeinsam verwendet werden können")
    void shouldWorkWithUserAndTaskTogether() {
        assertNotNull(user.getName());
        assertNotNull(task1.getAssignedTo());

        // Beispiel: Aufgabe ist einem User zugewiesen
        assertEquals("Max Mustermann", task1.getAssignedTo());
        assertEquals("Max Mustermann", user.getName());
    }
}