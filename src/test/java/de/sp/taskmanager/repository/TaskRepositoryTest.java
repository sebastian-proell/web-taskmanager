package de.sp.taskmanager.repository;

import de.sp.taskmanager.model.Task;
import de.sp.taskmanager.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Diese Klasse enthält Integrationstests für das TaskRepository.
 * DataJpaTest testet nur die JPA-Schicht mit einer In-Memory-Datenbank.
 *
 * Good Practice: Repository-Tests konzentrieren sich ausschließlich auf Datenbank-Operationen.
 * @DataJpaTest lädt nur JPA-Beans und verwendet eine separate Test-Datenbank.
 *
 * Wichtig zu wissen: Tests mit @DataJpaTest sind schneller als @SpringBootTest, da nur die
 * Persistenz-Schicht geladen wird. Die Datenbank wird nach jedem Test automatisch zurückgesetzt.
 */
@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    /**
     * Testet das Speichern und Abrufen eines Tasks.
     *
     * Good Practice: Jeder Test erstellt eigene Testdaten und prüft exakt das erwartete Verhalten.
     *
     * Wichtig zu wissen: assertNotNull und assertEquals stammen aus JUnit. Das Repository speichert
     * das Entity und generiert automatisch eine ID.
     */
    @Test
    void saveAndFindById_shouldWork() {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setStatus(TaskStatus.OPEN);
        task.setDueDate(LocalDateTime.now());

        Task saved = taskRepository.save(task);

        assertNotNull(saved.getId());
        Task found = taskRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Test Task", found.getTitle());
    }
}
