package de.sp.taskmanager.service;

import de.sp.taskmanager.model.Task;
import de.sp.taskmanager.repository.TaskRepository;
import de.sp.taskmanager.dto.TaskRequest;
import de.sp.taskmanager.dto.TaskResponse;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Diese Klasse enthält die Geschäftslogik für Tasks, wie Abrufen, Erstellen und Löschen.
 * Sie ist ein Service, der zwischen Controller und Repository vermittelt.
 *
 * Good Practice: Services handhaben Logik, nicht Controller. BeanUtils kopiert Eigenschaften effizient.
 * Streams für Sammlungen verwenden, um Code lesbar zu halten.
 *
 * Wichtig zu wissen: @Service markiert diese Klasse als Spring-Bean. Dependency Injection injiziert das Repository.
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    /**
     * Konstruktor, injiziert das Repository.
     *
     * Good Practice: Konstruktor-Injection für Testbarkeit.
     */
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Holt alle Tasks und konvertiert sie zu Responses.
     *
     * Good Practice: Immer Entities zu DTOs konvertieren, bevor sie zurückgegeben werden.
     */
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Holt ein Task per ID oder wirft eine Exception.
     *
     * Good Practice: orElseThrow für Fehlerbehandlung.
     */
    public TaskResponse getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(this::convertToResponse)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    /**
     * Erstellt ein neues Task aus dem Request.
     *
     * Good Practice: Kopiere nur notwendige Felder, um Sicherheitslücken zu vermeiden.
     */
    public TaskResponse createTask(TaskRequest request) {
        Task task = new Task();
        BeanUtils.copyProperties(request, task);
        Task saved = taskRepository.save(task);
        return convertToResponse(saved);
    }

    /**
     * Aktualisiert ein Task.
     *
     * Good Practice: Existenz prüfen vor Update.
     */
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        BeanUtils.copyProperties(request, task);
        Task updated = taskRepository.save(task);
        return convertToResponse(updated);
    }

    /**
     * Löscht ein Task per ID.
     *
     * Good Practice: Keine Rückgabe, da es eine Löschoperation ist.
     */
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    /**
     * Konvertiert ein Task zu einem Response-DTO.
     *
     * Good Practice: Private Hilfsmethoden für wiederholte Logik.
     */
    private TaskResponse convertToResponse(Task task) {
        TaskResponse response = new TaskResponse();
        BeanUtils.copyProperties(task, response);
        return response;
    }
}
