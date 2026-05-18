/**
 * TaskList-Komponente – Reine Präsentationskomponente für die Task-Liste.
 *
 * Diese Komponente ist bewusst schlank gehalten und enthält **keine** Formularlogik mehr.
 * Sie zeigt lediglich die Liste der Tasks an und bietet Aktionen wie Bearbeiten und Löschen.
 *
 * Best Practice:
 * - Komponenten sollten möglichst eine klare, einzelne Verantwortung haben (Single Responsibility)
 * - Formular und Liste sind nun vollständig getrennt
 * - State-Management bleibt im useTasks Hook gekapselt
 * - Die Komponente bleibt wiederverwendbar und einfach testbar
 *
 * Wichtig zu wissen:
 * Durch das Entfernen der TaskForm aus der TaskList wird die Architektur deutlich cleaner.
 * Die Liste ist jetzt eine reine View-Komponente. Das Formular zur Bearbeitung
 * befindet sich separat auf der linken Seite der TaskManagement-Seite.
 * Dadurch wird die Trennung von Anzeige und Bearbeitung klarer und die
 * Wartbarkeit der Anwendung verbessert.
 */
interface TaskListProps {
    tasks: any[];
    loading: boolean;
    error: string | null;
    onEdit: (task: any) => void;
    onDelete: (id: number) => void;
}

export default function TaskList({ tasks, loading, error, onEdit, onDelete }: TaskListProps) {

    if (loading) return <p>Lade Aufgaben...</p>;
    if (error) return <p style={{ color: 'red', whiteSpace: 'pre-wrap' }}>Fehler: {error}</p>;

    return (
        <div>
            {tasks.length === 0 ? (
                <p>Keine Tasks vorhanden.</p>
            ) : (
                <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                    <thead>
                    <tr>
                        <th style={{ textAlign: 'left', padding: '8px' }}>ID</th>
                        <th style={{ textAlign: 'left', padding: '8px' }}>Titel</th>
                        <th style={{ textAlign: 'left', padding: '8px' }}>Status</th>
                        <th style={{ textAlign: 'left', padding: '8px' }}>Fällig</th>
                        <th style={{ textAlign: 'left', padding: '8px' }}>Zugewiesen an</th>
                        <th style={{ textAlign: 'center', padding: '8px' }}>Aktionen</th>
                    </tr>
                    </thead>
                    <tbody>
                    {tasks.map(task => (
                        <tr key={task.id}>
                            <td style={{ padding: '8px' }}>{task.id}</td>
                            <td style={{ padding: '8px' }}>{task.title}</td>
                            <td style={{ padding: '8px' }}>{task.status}</td>
                            <td style={{ padding: '8px' }}>
                                {new Date(task.dueDate).toLocaleDateString('de-DE')}
                            </td>
                            <td style={{ padding: '8px' }}>{task.assignedTo}</td>
                            <td style={{ textAlign: 'center', padding: '8px' }}>
                                <button onClick={() => onEdit(task)}>
                                    Bearbeiten
                                </button>
                                <button
                                    onClick={() => onDelete(task.id)}
                                    style={{ marginLeft: '8px', color: '#e74c3c' }}
                                >
                                    Löschen
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}