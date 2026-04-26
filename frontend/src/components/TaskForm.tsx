import { useState, useEffect } from 'react'

interface TaskFormProps {
    task?: any          // Optional: Task, der bearbeitet werden soll
    onSaved: () => void // Callback, der nach erfolgreichem Speichern aufgerufen wird
}

/**
 * TaskForm-Komponente – Formular zum Anlegen und Bearbeiten von Tasks.
 *
 * Diese Komponente:
 * - Zeigt ein Formular mit allen Task-Feldern an
 * - Erkennt automatisch, ob ein neuer Task angelegt oder ein bestehender bearbeitet wird
 * - Sendet die Daten per POST (neu) oder PUT (bearbeiten) an das Backend
 * - Setzt das Formular nach dem Speichern zurück
 *
 * Good Practice: Die Formularlogik (Zustand, Validierung, API-Aufruf) ist
 * vollständig in dieser Komponente gekapselt – sie ist wiederverwendbar.
 *
 * Für Anfänger: Die Komponente verwendet "Controlled Components" – der Wert
 * jedes Eingabefelds wird über den React-State gesteuert.
 */
export default function TaskForm({ task, onSaved }: TaskFormProps) {
    // Formular-Zustand – wird bei jedem Tastendruck aktualisiert
    const [formData, setFormData] = useState({
        title: '',
        description: '',
        status: 'OPEN',
        dueDate: '',
        assignedTo: ''
    })

    // Zugangsdaten aus Environment-Variablen laden
    const username = import.meta.env.VITE_API_USERNAME
    const password = import.meta.env.VITE_API_PASSWORD

    /**
     * useEffect – wird ausgeführt, wenn sich der übergebene "task"-Prop ändert.
     *
     * Wenn ein Task zum Bearbeiten übergeben wird, werden die Formularfelder
     * mit den vorhandenen Werten gefüllt. Bei einem neuen Task bleibt das
     * Formular leer.
     */
    useEffect(() => {
        if (task) {
            setFormData({
                title: task.title,
                description: task.description || '',
                status: task.status,
                dueDate: task.dueDate ? task.dueDate.substring(0, 16) : '',
                assignedTo: task.assignedTo || ''
            })
        }
    }, [task])

    /**
     * Verarbeitet das Absenden des Formulars.
     *
     * Je nachdem, ob ein Task bearbeitet wird oder nicht, wird entweder
     * ein PUT-Request (Update) oder ein POST-Request (Create) gesendet.
     */
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault() // Verhindert das Standard-Formular-Verhalten (Seiten-Reload)

        const credentials = btoa(`${username}:${password}`)

        // URL und HTTP-Methode abhängig vom Modus (neu oder bearbeiten) wählen
        const url = task
            ? `http://localhost:8080/api/tasks/${task.id}`
            : 'http://localhost:8080/api/tasks'

        const method = task ? 'PUT' : 'POST'

        try {
            const response = await fetch(url, {
                method: method,
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Basic ${credentials}`
                },
                body: JSON.stringify(formData)
            })

            if (response.ok) {
                onSaved() // Parent-Komponente (TaskList) benachrichtigen
                // Formular zurücksetzen
                setFormData({ title: '', description: '', status: 'OPEN', dueDate: '', assignedTo: '' })
            } else {
                console.error('Save failed with status', response.status)
            }
        } catch (err) {
            console.error('Error saving task', err)
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <h3>{task ? 'Task bearbeiten' : 'Neuen Task anlegen'}</h3>

            <label>Titel</label>
            <input
                type="text"
                value={formData.title}
                onChange={e => setFormData({...formData, title: e.target.value})}
                required
            />

            <label>Beschreibung</label>
            <textarea
                value={formData.description}
                onChange={e => setFormData({...formData, description: e.target.value})}
            />

            <label>Status</label>
            <select
                value={formData.status}
                onChange={e => setFormData({...formData, status: e.target.value})}
            >
                <option value="OPEN">Open</option>
                <option value="IN_PROGRESS">In Progress</option>
                <option value="COMPLETED">Completed</option>
                <option value="BLOCKED">Blocked</option>
            </select>

            <label>Fälligkeitsdatum</label>
            <input
                type="datetime-local"
                value={formData.dueDate}
                onChange={e => setFormData({...formData, dueDate: e.target.value})}
            />

            <label>Zugewiesen an</label>
            <input
                type="text"
                value={formData.assignedTo}
                onChange={e => setFormData({...formData, assignedTo: e.target.value})}
            />

            <button type="submit">
                {task ? 'Aktualisieren' : 'Speichern'}
            </button>

            {/* Abbrechen-Button nur im Bearbeitungsmodus anzeigen */}
            {task && (
                <button type="button" onClick={() => onSaved()} style={{ marginLeft: '10px' }}>
                    Abbrechen
                </button>
            )}
        </form>
    )
}