package de.sp.taskmanager.repository;

import de.sp.taskmanager.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests für das TaskRepository und seine Implementierungen.
 */
class TaskRepositoryTest {

    private TaskRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTaskRepository();
    }

    @Test
    @DisplayName("Repository sollte eine Task speichern und wiederfinden können")
    void shouldSaveAndFindTaskById() {
        Task task = new Task("Test Task", "Beschreibung", TaskPriority.MEDIUM,
                LocalDate.now().plusDays(3), "Test User");

        repository.save(task);

        Task found = repository.findById(task.getId()).orElse(null);

        assertNotNull(found);
        assertEquals("Test Task", found.getTitle());
    }

    @Test
    @DisplayName("Repository sollte alle Tasks zurückgeben")
    void shouldReturnAllTasks() {
        repository.save(new Task("Task 1", "...", TaskPriority.LOW, LocalDate.now(), "User1"));
        repository.save(new Task("Task 2", "...", TaskPriority.HIGH, LocalDate.now(), "User2"));

        List<Task> allTasks = repository.findAll();
        assertEquals(2, allTasks.size());
    }

    @Test
    @DisplayName("Repository sollte eine Task löschen können")
    void shouldDeleteTask() {
        Task task = new Task("Zu löschende Task", "...", TaskPriority.LOW, LocalDate.now(), "User");
        repository.save(task);

        repository.delete(task);
        assertTrue(repository.findAll().isEmpty());
    }
}