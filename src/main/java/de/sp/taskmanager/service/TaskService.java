package de.sp.taskmanager.service;

import de.sp.taskmanager.model.Task;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TaskService – Fachliche Service-Schicht für Task-Operationen.
 *
 * Diese Klasse kapselt die Datenhaltung und Geschäftslogik. In dieser reinen JSF-Variante
 * wird eine einfache In-Memory-Liste verwendet, um den Fokus auf die Datenbindung
 * und die Backing Bean zu legen.
 *
 * Good Practice:
 * - @ApplicationScoped für eine langlebige, geteilte Instanz
 * - Trennung von Fachlogik und Präsentationslogik
 * - Defensive Kopien bei der Rückgabe von Listen
 * - Klare CRUD-Methoden
 *
 * Wichtig zu wissen:
 * Im Gegensatz zur React-Variante (wo der Service über HTTP aufgerufen wird) wird
 * dieser Service direkt von der Backing Bean injiziert und aufgerufen. Die Daten
 * verbleiben im Anwendungskontext des Servers.
 */
@ApplicationScoped
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public TaskService() {
        // Demo-Daten für den Unterricht
        tasks.add(new Task(idGenerator.getAndIncrement(), "Anforderungen sammeln", "Initiale Analyse", "OPEN", null, "Max Mustermann"));
        tasks.add(new Task(idGenerator.getAndIncrement(), "UI Mockups erstellen", "Dashboard und Formulare", "IN_PROGRESS", null, "Anna Schmidt"));
    }

    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }

    public Optional<Task> findById(Long id) {
        return tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(idGenerator.getAndIncrement());
            tasks.add(task);
        } else {
            findById(task.getId()).ifPresent(existing -> {
                existing.setTitle(task.getTitle());
                existing.setDescription(task.getDescription());
                existing.setStatus(task.getStatus());
                existing.setDueDate(task.getDueDate());
                existing.setAssignedTo(task.getAssignedTo());
            });
        }
        return task;
    }

    public void delete(Long id) {
        tasks.removeIf(t -> t.getId().equals(id));
    }
}