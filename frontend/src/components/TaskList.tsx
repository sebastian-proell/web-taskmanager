import TaskForm from './TaskForm.tsx';
import { useTasks } from '../hooks/useTasks';

/**
 * TaskList-Komponente – Reine Präsentationskomponente für die Task-Liste.
 *
 * Sie nutzt den useTasks Hook für die gesamte State-Logik (Laden, Löschen, Bearbeiten).
 * Die Komponente konzentriert sich ausschließlich auf das Rendering.
 *
 * Good Practice:
 * - Komponente bleibt schlank (nur UI)
 * - State-Management komplett im Hook gekapselt
 * - Klare Trennung von View und Logik
 *
 * Wichtig zu wissen:
 * Durch die Verwendung des useTasks Hooks wird die Zustandsverwaltung
 * zentral und konsistent gehalten. Die Komponente muss keine eigenen
 * useState-Hooks mehr verwalten.
 */
export default function TaskList() {
    const {
        tasks,
        loading,
        error,
        editingTask,
        deleteTask,
        startEditing,
        cancelEditing,
        refreshAfterSave
    } = useTasks();

    if (loading) return <p>Lade Aufgaben...</p>;
    if (error) return <p style={{ color: 'red', whiteSpace: 'pre-wrap' }}>Fehler: {error}</p>;

    return (
        <div>
            <h2>Aufgaben verwalten</h2>

            <TaskForm
                task={editingTask}
                onSaved={refreshAfterSave}
            />

            {editingTask && (
                <button
                    onClick={cancelEditing}
                    style={{ marginBottom: '16px', color: '#666' }}
                >
                    Bearbeitung abbrechen
                </button>
            )}

            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Titel</th>
                    <th>Status</th>
                    <th>Fällig</th>
                    <th>Zugewiesen an</th>
                    <th style={{ textAlign: 'center' }}>Aktionen</th>
                </tr>
                </thead>
                <tbody>
                {tasks.map(task => (
                    <tr key={task.id}>
                        <td>{task.id}</td>
                        <td>{task.title}</td>
                        <td>{task.status}</td>
                        <td>{new Date(task.dueDate).toLocaleDateString('de-DE')}</td>
                        <td>{task.assignedTo}</td>
                        <td style={{ textAlign: 'center' }}>
                            <button onClick={() => startEditing(task)}>Bearbeiten</button>
                            <button onClick={() => deleteTask(task.id)} style={{ marginLeft: '8px', color: '#e74c3c' }}>
                                Löschen
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}