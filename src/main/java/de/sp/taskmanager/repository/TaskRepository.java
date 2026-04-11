package de.sp.taskmanager.repository;

import de.sp.taskmanager.model.Task;

import java.util.List;
import java.util.Optional;

/**
 * Interface für den Zugriff auf Tasks.
 *
 * Dieses Interface abstrahiert den Datenzugriff.
 * Es ermöglicht später, die In-Memory-Implementierung durch eine echte Datenbank-Implementierung
 * (z. B. mit JPA) auszutauschen, ohne den Rest der Anwendung zu ändern.
 */
public interface TaskRepository {

    /**
     * Speichert eine Task (neu oder aktualisiert).
     */
    void save(Task task);

    /**
     * Sucht eine Task anhand der ID.
     *
     * @return Optional mit der Task, falls gefunden, sonst leer
     */
    Optional<Task> findById(Long id);

    /**
     * Gibt alle Tasks zurück.
     */
    List<Task> findAll();

    /**
     * Löscht eine Task.
     */
    void delete(Task task);
}