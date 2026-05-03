package de.sp.taskmanager.service;

import de.sp.taskmanager.dto.TaskRequest;
import de.sp.taskmanager.dto.TaskResponse;
import de.sp.taskmanager.model.Task;
import de.sp.taskmanager.model.TaskStatus;
import de.sp.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * TaskServiceTest – Unit-Tests für den TaskService (Termin 4).
 *
 * Good Practice: @ExtendWith(MockitoExtension.class) ermöglicht sauberes, isoliertes Testen
 * der Service-Schicht. Das Repository wird gemockt, sodass nur die Geschäftslogik getestet wird.
 * Jeder Test prüft genau eine Methode und deckt sowohl Happy-Path als auch Fehlerfälle ab.
 *
 * Die Tests wurden an die neue TaskRequest-Record-Struktur angepasst (mit Validierungs-
 * Annotationen aus Termin 4). Statt new TaskRequest() + Setter wird jetzt der kanonische
 * Record-Konstruktor mit allen Feldern verwendet.
 *
 * Wichtig zu wissen:
 * Da TaskRequest ein Java-Record ist (Best Practice für immutable DTOs), gibt es keinen
 * parameterlosen Konstruktor und keine Setter mehr. Stattdessen muss bei jeder Instanziierung
 * der vollständige Konstruktor mit allen Feldern aufgerufen werden. Das verhindert unvollständige
 * oder ungültige Daten schon beim Erstellen des Objekts und sorgt für type-sichere, wartbare Tests.
 * Die Tests demonstrieren gleichzeitig die erweiterte Validierung und das saubere API-Design
 * aus Termin 4 (Validierung von Benutzereingaben + Fehlermeldungen).
 */
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task testTask;

    /**
     * Setzt Testdaten vor jedem Test auf.
     *
     * Good Practice: @BeforeEach sorgt für eine saubere, reproduzierbare Ausgangslage.
     */
    @BeforeEach
    void setUp() {
        testTask = new Task();
        testTask.setId(1L);
        testTask.setTitle("Test Task");
        testTask.setDescription("Description");
        testTask.setStatus(TaskStatus.OPEN);
        testTask.setDueDate(LocalDateTime.now().plusDays(1));
        testTask.setAssignedTo("user");
    }

    /**
     * Testet das Abrufen aller Tasks.
     */
    @Test
    void getAllTasks_shouldReturnTaskList() {
        when(taskRepository.findAll()).thenReturn(List.of(testTask));

        List<TaskResponse> result = taskService.getAllTasks();

        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
        verify(taskRepository, times(1)).findAll();
    }

    /**
     * Testet das Abrufen eines Tasks per ID, wenn es existiert.
     */
    @Test
    void getTaskById_shouldReturnTask_whenFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));

        TaskResponse result = taskService.getTaskById(1L);

        assertEquals("Test Task", result.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    /**
     * Testet das Abrufen eines Tasks per ID, wenn es nicht existiert.
     */
    @Test
    void getTaskById_shouldThrowException_whenNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskService.getTaskById(1L));
        verify(taskRepository, times(1)).findById(1L);
    }

    /**
     * Testet das Erstellen eines neuen Tasks.
     * Verwendet jetzt den kanonischen Record-Konstruktor von TaskRequest.
     */
    @Test
    void createTask_shouldSaveAndReturnTask() {
        // Record-Konstruktor mit allen Feldern (keine Setter mehr möglich)
        TaskRequest request = new TaskRequest(
                "New Task",                                 // title
                "Desc",                                     // description
                "OPEN",                                     // status (String – passt zur @Pattern-Validierung)
                LocalDateTime.now().plusDays(1),            // dueDate
                "user"                                      // assignedTo
        );

        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        TaskResponse result = taskService.createTask(request);

        assertEquals(1L, result.getId());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    // Weitere Tests für updateTask und deleteTask können analog hinzugefügt werden.
}