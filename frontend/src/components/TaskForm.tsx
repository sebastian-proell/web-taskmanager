import ValidationErrorDisplay from './ValidationErrorDisplay';
import TaskFormField from './TaskFormField';
import { useTaskForm } from '../hooks/useTaskForm';
import { Button, CircularProgress, Stack } from '@mui/material';

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
 * TaskForm – Formular zum Erstellen und Bearbeiten von Tasks.
 *
 * Unterstützt sowohl den Create- als auch den Edit-Modus.
 * Wird `task` übergeben, wechselt das Formular automatisch in den Bearbeitungsmodus.
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
        <Stack spacing={3}>
            <ValidationErrorDisplay
                fieldErrors={fieldErrors}
                rawErrorDebug={rawErrorDebug}
                generalError={generalError}
                successMessage={successMessage}
            />

            <form onSubmit={handleSubmit}>
                <Stack spacing={3}>
                    <TaskFormField
                        id="title"
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
                        label="Beschreibung"
                        type="textarea"
                        value={formData.description}
                        onChange={handleInputChange}
                        error={fieldErrors.description}
                        disabled={isSubmitting}
                    />

                    <TaskFormField
                        id="status"
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
                        label="Fälligkeitsdatum"
                        type="datetime-local"
                        value={formData.dueDate}
                        onChange={handleInputChange}
                        error={fieldErrors.dueDate}
                        disabled={isSubmitting}
                    />

                    <TaskFormField
                        id="assignedTo"
                        label="Zugewiesen an"
                        type="text"
                        value={formData.assignedTo}
                        onChange={handleInputChange}
                        error={fieldErrors.assignedTo}
                        disabled={isSubmitting}
                    />

                    <Button
                        type="submit"
                        variant="contained"
                        size="large"
                        disabled={isSubmitting}
                        startIcon={
                            isSubmitting ? <CircularProgress size={20} color="inherit" /> : null
                        }
                    >
                        {isSubmitting
                            ? (isEditing ? 'Wird aktualisiert...' : 'Wird gespeichert...')
                            : (isEditing ? 'Task aktualisieren' : 'Task erstellen')}
                    </Button>
                </Stack>
            </form>
        </Stack>
    );
};

export default TaskForm;