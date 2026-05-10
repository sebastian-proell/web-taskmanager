package de.sp.taskmanager.bean;

import de.sp.taskmanager.model.Task;
import de.sp.taskmanager.service.TaskService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * TaskBean – Zentrale Backing Bean der JSF-Anwendung.
 *
 * Diese Bean ist für die serverseitige Datenbindung, die Verwaltung des View-States
 * und die Bereitstellung von Dashboard-Statistiken verantwortlich. Sie wird als
 * @SessionScoped Bean geführt, sodass der Zustand (insbesondere das aktuell bearbeitete
 * Task-Objekt und die Aufgabenliste) über mehrere Requests hinweg erhalten bleibt.
 *
 * Good Practice:
 * - Klare Trennung zwischen Präsentationslogik (Bean) und Fachlogik (TaskService)
 * - Verwendung von @Inject für lose Kopplung
 * - Explizite Methoden für CRUD-Operationen (save, edit, delete, cancel)
 * - Bereitstellung von abgeleiteten Statistiken für Dashboard-Übersichten
 * - @PostConstruct für saubere Initialisierung
 *
 * Wichtig zu wissen:
 * Durch die direkte Bindung der Eingabekomponenten an Eigenschaften dieser Bean
 * (z. B. value="#{taskBean.selectedTask.title}") übernimmt JSF automatisch die Werte
 * aus dem Formular in das Bean-Objekt (Phase "Update Model Values"). Dies ist der
 * klassische serverseitige Datenbindungs-Mechanismus von JSF. Zusätzlich ermöglichen
 * Getter-Methoden für Statistiken eine einfache Integration von Übersichts-Karten
 * im Frontend ohne zusätzliche Logik in der View.
 */
@Named
@SessionScoped
public class TaskBean implements Serializable {

    @Inject
    private TaskService taskService;

    private List<Task> tasks;
    private Task selectedTask;
    private boolean editMode = false;

    @PostConstruct
    public void init() {
        loadTasks();
        createNewTask();
    }

    public void loadTasks() {
        tasks = taskService.findAll();
    }

    public void createNewTask() {
        selectedTask = new Task();
        editMode = false;
    }

    /**
     * Speichert einen neuen oder bearbeiteten Task.
     *
     * Durch die Wertbindung in der XHTML-Seite sind die aktuellen Formularwerte
     * bereits in selectedTask enthalten. Die Methode delegiert lediglich an den Service.
     */
    public void save() {
        taskService.save(selectedTask);
        loadTasks();
        createNewTask();
    }

    public void edit(Task task) {
        this.selectedTask = task;
        this.editMode = true;
    }

    public void delete(Task task) {
        taskService.delete(task.getId());
        loadTasks();
    }

    public void cancel() {
        createNewTask();
    }

    // --- Getter & Setter für die Datenbindung in der XHTML-Seite ---

    public List<Task> getTasks() {
        return tasks;
    }

    public Task getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }

    public boolean isEditMode() {
        return editMode;
    }

    // --- Dashboard-Statistiken für die Übersichtsseite ---

    /**
     * Liefert die Gesamtzahl aller Tasks.
     * Wird für Dashboard-Karten verwendet.
     */
    public long getTotalTasks() {
        return tasks != null ? tasks.size() : 0;
    }

    /**
     * Zählt Tasks mit Status "OPEN".
     */
    public long getOpenTasks() {
        if (tasks == null) return 0;
        return tasks.stream()
                .filter(t -> "OPEN".equals(t.getStatus()))
                .count();
    }

    /**
     * Zählt Tasks mit Status "IN_PROGRESS".
     */
    public long getInProgressTasks() {
        if (tasks == null) return 0;
        return tasks.stream()
                .filter(t -> "IN_PROGRESS".equals(t.getStatus()))
                .count();
    }

    /**
     * Zählt Tasks mit Status "COMPLETED".
     */
    public long getCompletedTasks() {
        if (tasks == null) return 0;
        return tasks.stream()
                .filter(t -> "COMPLETED".equals(t.getStatus()))
                .count();
    }
}