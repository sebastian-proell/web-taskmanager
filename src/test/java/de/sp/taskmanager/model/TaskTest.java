package de.sp.taskmanager.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testklasse für die Task-Entity.
 */
class TaskTest {

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task(
                "Testaufgabe schreiben",
                "JUnit-Test für die Task-Klasse implementieren",
                TaskPriority.HIGH,
                LocalDate.of(2026, 4, 15),
                "Anna Schmidt"
        );
    }

    @Test
    @DisplayName("Neue Aufgabe sollte den Status OPEN haben")
    void shouldHaveOpenStatusAfterCreation() {
        assertEquals(TaskStatus.OPEN, task.getStatus());
    }

    @Test
    @DisplayName("markAsDone() sollte den Status auf DONE setzen")
    void shouldSetStatusToDone() {
        task.markAsDone();
        assertEquals(TaskStatus.DONE, task.getStatus());
    }

    @Test
    @DisplayName("isOverdue() sollte true zurückgeben, wenn Fälligkeitsdatum in der Vergangenheit liegt")
    void shouldBeOverdueWhenDueDateIsInPast() {
        task.setDueDate(LocalDate.of(2025, 1, 1));
        assertTrue(task.isOverdue());
    }

    @Test
    @DisplayName("isOverdue() sollte false zurückgeben, wenn die Aufgabe DONE ist")
    void shouldNotBeOverdueWhenTaskIsDone() {
        task.setDueDate(LocalDate.of(2025, 1, 1));
        task.markAsDone();
        assertFalse(task.isOverdue());
    }

    @Test
    @DisplayName("increaseEstimatedHours() sollte den Aufwand korrekt erhöhen")
    void shouldIncreaseEstimatedHoursCorrectly() {
        task.increaseEstimatedHours(5.5);
        assertEquals(5.5, task.getEstimatedHours());

        task.increaseEstimatedHours(2.0);
        assertEquals(7.5, task.getEstimatedHours());
    }
}