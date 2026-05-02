package de.sp.taskmanager.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit-Tests für die Task-Entity (reines POJO-Testing ohne Datenbank).
 *
 * Good Practice: Jede Entity sollte isolierte Unit-Tests haben. Dadurch wird sichergestellt,
 * dass Getter, Setter, Default-Konstruktor und Enum-Handling korrekt funktionieren,
 * bevor die Klasse in Service, Repository oder Controller verwendet wird.
 * Das erleichtert späteres Refactoring und macht den Code wartbar.
 *
 * Wichtig zu wissen: @Test-Methoden testen einzelne Verhaltensweisen (Single Responsibility).
 * @BeforeEach sorgt dafür, dass vor jedem Test ein frisches Task-Objekt existiert.
 * Wir verwenden JUnit 5 (bereits im Spring-Boot-Starter-Test enthalten).
 */
class TaskTest {

    private Task task;
    private LocalDateTime testDueDate;

    /**
     * Wird vor jedem Test ausgeführt – stellt ein konsistentes Test-Setup sicher.
     */
    @BeforeEach
    void setUp() {
        task = new Task();
        testDueDate = LocalDateTime.now().plusDays(7);
    }

    @Test
    void defaultConstructor_shouldInitializeAllFieldsToNull() {
        assertNotNull(task, "Task-Objekt sollte erzeugt werden");
        assertNull(task.getId());
        assertNull(task.getTitle());
        assertNull(task.getDescription());
        assertNull(task.getStatus());
        assertNull(task.getDueDate());
        assertNull(task.getAssignedTo());
    }

    @Test
    void shouldCorrectlySetAndGetAllFields() {
        // Arrange
        task.setTitle("Test-Aufgabe Termin 2");
        task.setDescription("Dies ist eine Beispielaufgabe für die Unit-Tests");
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setDueDate(testDueDate);
        task.setAssignedTo("max.mustermann");

        // Act & Assert
        assertEquals("Test-Aufgabe Termin 2", task.getTitle());
        assertEquals("Dies ist eine Beispielaufgabe für die Unit-Tests", task.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
        assertEquals(testDueDate, task.getDueDate());
        assertEquals("max.mustermann", task.getAssignedTo());
        // ID wird erst durch JPA vergeben → bleibt null
        assertNull(task.getId());
    }

    @Test
    void shouldHandleAllPossibleTaskStatuses() {
        for (TaskStatus status : TaskStatus.values()) {
            task.setStatus(status);
            assertEquals(status, task.getStatus(),
                    "Status " + status + " sollte korrekt gesetzt werden");
        }
    }

    @Test
    void dueDate_shouldBeMutableAndAcceptNull() {
        LocalDateTime original = LocalDateTime.now().plusDays(3);
        task.setDueDate(original);
        assertEquals(original, task.getDueDate());

        task.setDueDate(null);
        assertNull(task.getDueDate(), "DueDate sollte auf null gesetzt werden können");
    }

    @Test
    void assignedTo_shouldAcceptAnyStringIncludingNull() {
        task.setAssignedTo("user");
        assertEquals("user", task.getAssignedTo());

        task.setAssignedTo(null);
        assertNull(task.getAssignedTo());
    }
}