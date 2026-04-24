package de.sp.taskmanager.repository;

import de.sp.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Dieses Interface ist ein Repository für Tasks, das CRUD-Operationen bereitstellt.
 * Es erweitert JpaRepository, das Standardmethoden wie save() und findAll() bietet.
 *
 * Good Practice: Repositories abstrahieren den Datenbankzugriff. @Repository markiert es als Spring-Bean.
 * Keine Implementierung notwendig – Spring generiert sie.
 *
 * Wichtig zu wissen: JPA-Repositories vereinfachen Datenbankinteraktionen. Task ist die Entity, Long der ID-Type.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
