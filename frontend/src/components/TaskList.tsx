import { useEffect, useState } from 'react'
import TaskForm from './TaskForm.tsx'

interface Task {
    id: number
    title: string
    description: string
    status: string
    dueDate: string
    assignedTo: string
}

/**
 * TaskList-Komponente – Zeigt alle Tasks in einer Tabelle an und ermöglicht CRUD-Operationen.
 *
 * Diese Komponente ist das Herzstück der Anwendung. Sie:
 * - Lädt alle Tasks vom Backend beim ersten Rendern
 * - Ermöglicht das Erstellen neuer Tasks über das TaskForm
 * - Ermöglicht das Bearbeiten bestehender Tasks (durch Klick auf "Bearbeiten")
 * - Ermöglicht das Löschen von Tasks mit Bestätigungsdialog
 *
 * Good Practice: Die Zugangsdaten werden aus Environment-Variablen geladen
 * und nie hartcodiert im Quellcode gespeichert. Das erhöht die Sicherheit.
 *
 * Für Anfänger: Die Komponente verwendet React Hooks (useState, useEffect),
 * um Daten zu verwalten und API-Aufrufe durchzuführen.
 */
export default function TaskList() {
    // State-Variablen für die Verwaltung der Tasks und des UI-Zustands
    const [tasks, setTasks] = useState<Task[]>([])           // Liste aller Tasks
    const [loading, setLoading] = useState(true)             // Ladezustand (zeigt "Lade Aufgaben..." an)
    const [error, setError] = useState<string | null>(null)  // Fehlermeldung bei API-Fehlern
    const [editingTask, setEditingTask] = useState<Task | null>(null) // Task, der gerade bearbeitet wird

    // Zugangsdaten aus der .env-Datei laden (Vite ersetzt diese Werte beim Build)
    const username = import.meta.env.VITE_API_USERNAME
    const password = import.meta.env.VITE_API_PASSWORD

    /**
     * Lädt alle Tasks vom Backend.
     *
     * Diese Funktion:
     * 1. Setzt den Ladezustand auf true
     * 2. Erzeugt die Basic-Auth-Credentials (Base64-kodiert)
     * 3. Ruft die REST-API mit Authorization-Header auf
     * 4. Verarbeitet die Antwort und aktualisiert den State
     *
     * Good Practice: Fehlerbehandlung ist zentralisiert – sowohl HTTP-Fehler
     * als auch Netzwerkfehler werden abgefangen und dem Benutzer angezeigt.
     */
    const loadTasks = async () => {
        setLoading(true)
        setError(null)

        // Basic-Auth-Credentials erzeugen (Benutzername:Passwort → Base64)
        const credentials = btoa(`${username}:${password}`)

        try {
            const response = await fetch('http://localhost:8080/api/tasks', {
                method: 'GET',
                headers: {
                    'Authorization': `Basic ${credentials}`,
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                }
            })

            // Prüfen, ob die Anfrage erfolgreich war
            if (!response.ok) {
                throw new Error(`HTTP ${response.status} - ${response.statusText}`)
            }

            const data = await response.json()
            setTasks(data)        // Tasks im State speichern → Tabelle wird neu gerendert
            setError(null)
        } catch (err: any) {
            console.error('Fehler beim Laden der Tasks:', err)
            setError(err.message || 'Failed to fetch tasks')
        } finally {
            setLoading(false)     // Ladezustand in jedem Fall beenden
        }
    }

    /**
     * useEffect-Hook – wird einmal beim ersten Rendern der Komponente ausgeführt.
     *
     * Der leere Abhängigkeits-Array [] bewirkt, dass loadTasks() nur einmal
     * aufgerufen wird (beim Mounten der Komponente).
     */
    useEffect(() => {
        loadTasks()
    }, [])

    /**
     * Löscht einen Task nach Bestätigung durch den Benutzer.
     *
     * @param id Die ID des zu löschenden Tasks
     */
    const deleteTask = async (id: number) => {
        // Sicherheitsabfrage, damit nicht versehentlich Tasks gelöscht werden
        if (!window.confirm('Task wirklich löschen?')) return

        const credentials = btoa(`${username}:${password}`)

        try {
            const response = await fetch(`http://localhost:8080/api/tasks/${id}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Basic ${credentials}`
                }
            })

            if (response.ok) {
                loadTasks() // Tabelle nach erfolgreichem Löschen neu laden
            } else {
                console.error('Delete failed with status', response.status)
            }
        } catch (err) {
            console.error('Error deleting task', err)
        }
    }

    // Ladezustand anzeigen
    if (loading) return <p>Lade Aufgaben...</p>

    // Fehlermeldung anzeigen
    if (error) return <p style={{ color: 'red', whiteSpace: 'pre-wrap' }}>Fehler: {error}</p>

    return (
        <div>
            <h2>Aufgaben verwalten</h2>

            {/* TaskForm wird eingebunden. Beim Speichern wird die Tabelle neu geladen. */}
            <TaskForm
                task={editingTask}
                onSaved={() => {
                    setEditingTask(null)  // Bearbeitungsmodus beenden
                    loadTasks()           // Tabelle aktualisieren
                }}
            />

            {/* Tabelle mit allen Tasks */}
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
                            <button onClick={() => setEditingTask(task)}>Bearbeiten</button>
                            <button onClick={() => deleteTask(task.id)} style={{ marginLeft: '8px', color: '#e74c3c' }}>
                                Löschen
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    )
}