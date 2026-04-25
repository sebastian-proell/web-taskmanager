package de.sp.taskmanager.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import de.sp.taskmanager.dto.TaskRequest;
import de.sp.taskmanager.model.TaskStatus;

/**
 * Wiederverwendbare TaskForm-Komponente (Termin 3).
 *
 * Diese Klasse kapselt das gesamte Formular für das Anlegen/Bearbeiten eines Tasks.
 * Sie wird in der TaskView eingebunden und demonstriert hervorragend die
 * Wiederverwendbarkeit von Komponenten (zentrales Prinzip aus Lektion 3.1).
 *
 * Good Practice: Separate Form-Komponenten machen den Code übersichtlicher,
 * testbar und erweiterbar. Der Binder wird hier zentral verwaltet.
 *
 * Für Anfänger: Die Klasse erbt von FormLayout und stellt alle Felder + Binder
 * bereit. Über Getter kann die TaskView auf den Binder und die Buttons zugreifen.
 */
public class TaskForm extends FormLayout {

    private final Binder<TaskRequest> binder = new BeanValidationBinder<>(TaskRequest.class);

    private final TextField titleField = new TextField("Titel");
    private final TextArea descriptionField = new TextArea("Beschreibung");
    private final ComboBox<TaskStatus> statusCombo = new ComboBox<>("Status");
    private final DateTimePicker dueDatePicker = new DateTimePicker("Fälligkeitsdatum");
    private final TextField assignedToField = new TextField("Zugewiesen an");

    public TaskForm() {
        statusCombo.setItems(TaskStatus.values());
        statusCombo.setItemLabelGenerator(TaskStatus::name);

        add(titleField, descriptionField, statusCombo, dueDatePicker, assignedToField);

        // Binder konfigurieren
        binder.forField(titleField).bind(TaskRequest::getTitle, TaskRequest::setTitle);
        binder.forField(descriptionField).bind(TaskRequest::getDescription, TaskRequest::setDescription);
        binder.forField(statusCombo).bind(TaskRequest::getStatus, TaskRequest::setStatus);
        binder.forField(dueDatePicker).bind(TaskRequest::getDueDate, TaskRequest::setDueDate);
        binder.forField(assignedToField).bind(TaskRequest::getAssignedTo, TaskRequest::setAssignedTo);
    }

    public Binder<TaskRequest> getBinder() {
        return binder;
    }

    public TaskRequest getCurrentBean() {
        TaskRequest bean = new TaskRequest();
        binder.writeBeanIfValid(bean);
        return bean;
    }

    public void setBean(TaskRequest bean) {
        binder.setBean(bean);
    }

    public void clear() {
        setBean(new TaskRequest());
    }
}