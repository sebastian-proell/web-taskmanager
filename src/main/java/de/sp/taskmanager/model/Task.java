package de.sp.taskmanager.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Task – Einfaches Domänenmodell für einen Task.
 *
 * Diese Klasse dient als Datencontainer. In einer realen Anwendung würde man
 * hier typischerweise JPA-Annotationen (@Entity, @Id etc.) hinzufügen.
 *
 * Good Practice:
 * - Implementiert Serializable (wichtig für JSF Session-Scoped Beans)
 * - Einfache POJO-Struktur mit Gettern und Settern
 *
 * Wichtig zu wissen:
 * Da diese JSF-Variante bewusst einfach gehalten ist (Fokus auf Datenbindung),
 * wird auf eine Datenbankanbindung verzichtet. Die Daten werden im TaskService
 * in einer In-Memory-Liste gehalten.
 */
public class Task implements Serializable {

    private Long id;
    private String title;
    private String description;
    private String status = "OPEN";
    private LocalDateTime dueDate;
    private String assignedTo;

    public Task() {}

    public Task(Long id, String title, String description, String status,
                LocalDateTime dueDate, String assignedTo) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.assignedTo = assignedTo;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }
}