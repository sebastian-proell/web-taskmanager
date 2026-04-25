package de.sp.taskmanager.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.sp.taskmanager.dto.TaskRequest;
import de.sp.taskmanager.dto.TaskResponse;
import de.sp.taskmanager.service.TaskService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TaskView – Hauptansicht für die Task-Verwaltung (Termin 3).
 *
 * Diese Klasse verwendet die separate, wiederverwendbare TaskForm-Komponente
 * und demonstriert damit perfekt die Prinzipien komponentenbasierter UIs
 * (Wiederverwendbarkeit, Komponentenbaum, klare Trennung von Verantwortlichkeiten).
 *
 * Good Practice: Die View konzentriert sich nur auf die Orchestrierung (Grid + Buttons),
 * während die Formularlogik komplett in TaskForm ausgelagert ist. Das macht den Code
 * übersichtlicher, testbar und erweiterbar (z. B. für Termin 5: Zustandsverwaltung).
 *
 * Für Anfänger: Die TaskView ist eine Vaadin-Route, die in das MainLayout eingebettet wird.
 * Der Grid zeigt alle Tasks an, beim Klick auf einen Task wird das Formular automatisch
 * mit den Daten gefüllt (Edit-Modus). Alle Operationen rufen direkt den TaskService aus
 * Termin 2 auf – keine manuellen REST-Aufrufe nötig.
 */
@Route(value = "tasks", layout = MainLayout.class)
@PageTitle("Aufgaben | TaskManager")
@PermitAll
public class TaskView extends VerticalLayout {

    private final TaskService taskService;
    private final TaskForm taskForm;           // wiederverwendbare Form-Komponente
    private final Grid<TaskResponse> taskGrid = new Grid<>(TaskResponse.class);

    private Long editingId = null;             // Merkt sich, welcher Task gerade bearbeitet wird

    /**
     * Konstruktor – Spring injiziert den TaskService und die TaskForm.
     */
    public TaskView(@Autowired TaskService taskService) {
        this.taskService = taskService;
        this.taskForm = new TaskForm();

        setSizeFull();
        addClassName("task-view");

        H1 title = new H1("Aufgaben verwalten");
        add(title);

        configureGrid();
        add(taskGrid);

        HorizontalLayout buttonLayout = createButtonLayout();

        add(taskForm, buttonLayout);

        refreshGrid();
    }

    private void configureGrid() {
        taskGrid.setSizeFull();
        taskGrid.setColumns("id", "title", "status", "dueDate", "assignedTo");
        taskGrid.getColumnByKey("dueDate").setHeader("Fällig");

        // Bei Auswahl eines Tasks im Grid → Formular befüllen (Edit-Modus)
        taskGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                TaskResponse selected = event.getValue();
                editingId = selected.getId();

                // Manuelle Zuweisung der Felder (kein Converter nötig)
                TaskRequest request = new TaskRequest();
                request.setTitle(selected.getTitle());
                request.setDescription(selected.getDescription());
                request.setStatus(selected.getStatus());
                request.setDueDate(selected.getDueDate());
                request.setAssignedTo(selected.getAssignedTo());

                taskForm.setBean(request);
            }
        });
    }

    private HorizontalLayout createButtonLayout() {
        Button saveButton = new Button("Speichern");
        Button deleteButton = new Button("Löschen");
        Button newButton = new Button("Neuer Task");
        Button refreshButton = new Button("Aktualisieren");

        saveButton.addClickListener(e -> saveTask());
        deleteButton.addClickListener(e -> deleteSelectedTask());
        newButton.addClickListener(e -> clearForm());
        refreshButton.addClickListener(e -> refreshGrid());

        return new HorizontalLayout(saveButton, deleteButton, newButton, refreshButton);
    }

    private void saveTask() {
        TaskRequest request = taskForm.getCurrentBean();

        if (binderIsValid(request)) {   // kleine Hilfsmethode, falls gewünscht
            if (editingId == null) {
                // Neuer Task
                taskService.createTask(request);
                Notification.show("✅ Neuer Task erfolgreich angelegt!");
            } else {
                // Bestehenden Task aktualisieren
                taskService.updateTask(editingId, request);
                Notification.show("✅ Task erfolgreich aktualisiert!");
                editingId = null;
            }
            clearForm();
            refreshGrid();
        }
    }

    private void deleteSelectedTask() {
        TaskResponse selected = taskGrid.asSingleSelect().getValue();
        if (selected != null) {
            taskService.deleteTask(selected.getId());
            Notification.show("🗑️ Task gelöscht!");
            refreshGrid();
            clearForm();
        } else {
            Notification.show("Bitte zuerst einen Task in der Tabelle auswählen.");
        }
    }

    private void clearForm() {
        taskForm.clear();
        editingId = null;
    }

    private void refreshGrid() {
        taskGrid.setItems(taskService.getAllTasks());
    }

    /**
     * Hilfsmethode – prüft, ob der Binder valide Daten enthält.
     * (Kann später in Termin 4 mit Bean Validation erweitert werden)
     */
    private boolean binderIsValid(TaskRequest request) {
        // Hier könnte später eine echte Validierung hin (z. B. @Valid)
        return request.getTitle() != null && !request.getTitle().isBlank();
    }
}