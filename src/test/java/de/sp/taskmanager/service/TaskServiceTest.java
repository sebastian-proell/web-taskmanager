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
 * Diese Klasse enthält Unit-Tests für den TaskService.
 * Der Service wird isoliert getestet, das Repository wird gemockt.
 *
 * Good Practice: MockitoExtension ermöglicht sauberes Mocking. Jeder Test prüft eine einzelne Methode.
 *
 * Wichtig zu wissen: @ExtendWith(MockitoExtension.class) aktiviert Mockito. @Mock erstellt einen
 * simulierten Repository, @InjectMocks fügt ihn in den Service ein.
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
     * Good Practice: @BeforeEach sorgt für eine saubere Ausgangslage für jeden Test.
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
     *
     * Good Practice: Fehlerfälle werden explizit getestet.
     */
    @Test
    void getTaskById_shouldThrowException_whenNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskService.getTaskById(1L));
        verify(taskRepository, times(1)).findById(1L);
    }

    /**
     * Testet das Erstellen eines neuen Tasks.
     */
    @Test
    void createTask_shouldSaveAndReturnTask() {
        TaskRequest request = new TaskRequest();
        request.setTitle("New Task");
        request.setDescription("Desc");
        request.setStatus(TaskStatus.OPEN);
        request.setDueDate(LocalDateTime.now());
        request.setAssignedTo("user");

        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        TaskResponse result = taskService.createTask(request);

        assertEquals(1L, result.getId());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    // Weitere Tests für updateTask und deleteTask können analog hinzugefügt werden.
}
