package de.sp.taskmanager.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Repräsentiert eine Aufgabe im TaskManager.
 *
 * Diese Klasse dient als zentrales Beispiel für objektorientierte Prinzipien:
 * - Vererbung (von AbstractEntity)
 * - Kapselung (private Attribute mit Getter/Setter)
 * - Einfache Business-Logik in Form von Methoden
 */
public class Task extends AbstractEntity {

    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private String assignedTo;
    private Double estimatedHours;

    /**
     * Erstellt eine neue Aufgabe.
     *
     * @param title       Kurzer Titel der Aufgabe
     * @param description Ausführliche Beschreibung
     * @param priority    Priorität der Aufgabe
     * @param dueDate     Fälligkeitsdatum
     * @param assignedTo  Name der zugewiesenen Person
     */
    public Task(String title, String description, TaskPriority priority,
                LocalDate dueDate, String assignedTo) {

        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.assignedTo = assignedTo;

        this.status = TaskStatus.OPEN;
        this.createdAt = LocalDateTime.now();
        this.estimatedHours = 0.0;
    }

    /**
     * Prüft, ob die Aufgabe überfällig ist.
     * Eine Aufgabe gilt als überfällig, wenn das Fälligkeitsdatum in der Vergangenheit liegt
     * und der Status noch nicht "DONE" ist.
     */
    public boolean isOverdue() {
        return dueDate != null
                && dueDate.isBefore(LocalDate.now())
                && status != TaskStatus.DONE;
    }

    /**
     * Setzt den Status der Aufgabe auf "DONE".
     */
    public void markAsDone() {
        this.status = TaskStatus.DONE;
    }

    /**
     * Erhöht den geschätzten Aufwand um die angegebene Stundenzahl.
     */
    public void increaseEstimatedHours(double hours) {
        this.estimatedHours += hours;
    }

    // Getter und Setter
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    public TaskPriority getPriority() { return priority; }
    public void setPriority(TaskPriority priority) { this.priority = priority; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }

    public Double getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(Double estimatedHours) { this.estimatedHours = estimatedHours; }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + getId() +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", assignedTo='" + assignedTo + '\'' +
                '}';
    }
}
