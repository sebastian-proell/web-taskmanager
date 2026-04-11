package de.sp.taskmanager.repository;

import de.sp.taskmanager.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Einfache In-Memory-Implementierung des TaskRepository.
 * Zeigt Polymorphie: Die konkrete Implementierung kann später ausgetauscht werden.
 */
public class InMemoryTaskRepository implements TaskRepository {

    private final List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public void save(Task task) {
        if (task.getId() == null) {
            task.setId(nextId++);
        }
        tasks.add(task);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return tasks.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }

    @Override
    public void delete(Task task) {
        tasks.removeIf(t -> t.getId().equals(task.getId()));
    }
}
