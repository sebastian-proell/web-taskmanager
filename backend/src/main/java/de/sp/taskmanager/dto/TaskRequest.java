package de.sp.taskmanager.dto;

import de.sp.taskmanager.model.TaskStatus;
import java.time.LocalDateTime;

/**
 * Diese Klasse ist ein Data Transfer Object (DTO) für Anfragen zum Erstellen oder Aktualisieren von Tasks.
 * Sie enthält Felder, die vom Client gesendet werden, ohne interne Details wie IDs.
 *
 * Good Practice: DTOs werden verwendet, um die API von der internen Datenstruktur zu trennen. Das schützt
 * sensible Daten und erleichtert Änderungen. Getter und Setter sorgen für Kapselung.
 *
 * Wichtig zu wissen: DTOs sind einfache Klassen ohne Logik, die Daten transportieren. Sie werden in Controllern
 * verwendet, um JSON in Objekte umzuwandeln.
 */
public class TaskRequest {

    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime dueDate;
    private String assignedTo;

    /**
     * Getter für den Titel.
     *
     * Good Practice: Immer Getter und Setter für Felder bereitstellen, um direkten Zugriff zu vermeiden
     * (Prinzip der Kapselung).
     */
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
