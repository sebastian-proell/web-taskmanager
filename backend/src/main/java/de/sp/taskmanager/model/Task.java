package de.sp.taskmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;

/**
 * Diese Klasse repräsentiert ein Task-Modell für die Datenbank.
 * Sie ist eine JPA-Entity, die in einer Tabelle gespeichert wird.
 *
 * Good Practice: Entities werden für Datenpersistenz verwendet. @Enumerated speichert Enums als Strings,
 * um Lesbarkeit zu gewährleisten. Getter/Setter für Kapselung.
 *
 * Wichtig zu wissen: JPA (Jakarta Persistence API) mappt Java-Objekte zu Datenbanktabellen.
 * @Entity markiert diese Klasse als persistierbar.
 */
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDateTime dueDate;
    private String assignedTo;

    /**
     * Getter für die ID.
     *
     * Good Practice: IDs werden automatisch generiert (IDENTITY-Strategie für Datenbanken wie MySQL).
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
