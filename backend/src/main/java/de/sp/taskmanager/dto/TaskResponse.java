package de.sp.taskmanager.dto;

import de.sp.taskmanager.model.TaskStatus;
import java.time.LocalDateTime;

/**
 * Diese Klasse ist ein Data Transfer Object (DTO) für Antworten, die Tasks an den Client senden.
 * Sie enthält alle relevanten Felder, inklusive ID, für die Rückgabe.
 *
 * Good Practice: Separate DTOs für Request und Response verwenden, falls sie unterschiedlich sind.
 * Das erlaubt Flexibilität, z. B. sensible Felder in Responses zu verstecken.
 *
 * Wichtig zu wissen: Diese Klasse wird in Controllern verwendet, um Daten als JSON zurückzugeben.
 * Getter und Setter ermöglichen den Zugriff auf die Felder.
 */
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime dueDate;
    private String assignedTo;

    /**
     * Getter für die ID.
     *
     * Good Practice: IDs sind oft Long, um große Werte zu handhaben. Immer Getter/Setter für alle Felder.
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}
