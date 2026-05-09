import React from 'react';
import ValidationErrorDisplay from './ValidationErrorDisplay';
import TaskFormField from './TaskFormField';
import { useTaskForm } from '../hooks/useTaskForm';

interface Task {
    id: number;
    title: string;
    description: string;
    status: string;
    dueDate: string;
    assignedTo: string;
}

interface TaskFormProps {
    task?: Task | null;
    onSaved: () => void;
}

/**
 * TaskForm ist die reine Präsentationskomponente für das Task-Formular.
 *
 * Sie nutzt den useTaskForm Hook für die gesamte State-Logik und Datenbindung.
 * Die Komponente selbst konzentriert sich nur noch auf das Rendering der UI-Elemente.
 *
 * Good Practice:
 * - Komponente bleibt schlank und fokussiert sich auf das Rendering (Separation of Concerns)
 * - Business-Logik und State-Management sind im wiederverwendbaren Hook gekapselt
 * - Einfache Props-Schnittstelle (task + onSaved)
 * - Klare Verantwortlichkeiten zwischen View und Logik
 *
 * Wichtig zu wissen:
 * Durch die Auslagerung der gesamten Formularlogik in den useTaskForm Hook
 * wird die Datenbindung zwischen View und Model sauber getrennt.
 * Die Komponente erhält alle benötigten Werte und Handler über den Hook
 * und bleibt dadurch einfach und gut testbar.
 */
const TaskForm: React.FC<TaskFormProps> = ({ task, onSaved }) => {
    const {
        formData,
        fieldErrors,
        generalError,
        successMessage,
        isSubmitting,
        isEditing,
        rawErrorDebug,
        handleInputChange,
        handleSubmit
    } = useTaskForm(task, onSaved);

    return (
        <div className="task-form-container">
            <h2>{isEditing ? 'Task bearbeiten' : 'Neuen Task erstellen'}</h2>

            <ValidationErrorDisplay
                fieldErrors={fieldErrors}
                rawErrorDebug={rawErrorDebug}
                generalError={generalError}
                successMessage={successMessage}
            />

            <form onSubmit={handleSubmit}>
                <TaskFormField
                    id="title"
                    name="title"
                    label="Titel"
                    type="text"
                    value={formData.title}
                    onChange={handleInputChange}
                    error={fieldErrors.title}
                    required
                    disabled={isSubmitting}
                />

                <TaskFormField
                    id="description"
                    name="description"
                    label="Beschreibung"
                    type="textarea"
                    value={formData.description}
                    onChange={handleInputChange}
                    error={fieldErrors.description}
                    disabled={isSubmitting}
                />

                <TaskFormField
                    id="status"
                    name="status"
                    label="Status"
                    type="select"
                    value={formData.status}
                    onChange={handleInputChange}
                    error={fieldErrors.status}
                    options={[
                        { value: 'OPEN', label: 'Offen' },
                        { value: 'IN_PROGRESS', label: 'In Bearbeitung' },
                        { value: 'COMPLETED', label: 'Abgeschlossen' },
                        { value: 'BLOCKED', label: 'Blockiert' }
                    ]}
                    required
                    disabled={isSubmitting}
                />

                <TaskFormField
                    id="dueDate"
                    name="dueDate"
                    label="Fälligkeitsdatum"
                    type="datetime-local"
                    value={formData.dueDate}
                    onChange={handleInputChange}
                    error={fieldErrors.dueDate}
                    disabled={isSubmitting}
                />

                <TaskFormField
                    id="assignedTo"
                    name="assignedTo"
                    label="Zugewiesen an"
                    type="text"
                    value={formData.assignedTo}
                    onChange={handleInputChange}
                    error={fieldErrors.assignedTo}
                    disabled={isSubmitting}
                />

                <button type="submit" disabled={isSubmitting}>
                    {isSubmitting
                        ? (isEditing ? 'Wird aktualisiert...' : 'Wird gespeichert...')
                        : (isEditing ? 'Task aktualisieren' : 'Task erstellen')}
                </button>
            </form>
        </div>
    );
};

export default TaskForm;