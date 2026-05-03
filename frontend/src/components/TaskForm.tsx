import React, { useState } from 'react';
import ValidationErrorDisplay from './ValidationErrorDisplay';
import TaskFormField from './TaskFormField';

/**
 * TaskForm.tsx ist die zentrale Komponente zur Erstellung und Bearbeitung von Tasks.
 *
 * Sie verarbeitet das Formular, sendet Daten an die REST-API und delegiert die
 * komplette Fehler- und Erfolgsdarstellung an wiederverwendbare Sub-Komponenten.
 *
 * Good Practice:
 * - Controlled Components mit minimalem Boilerplate
 * - Auslagerung der Fehlerdarstellung und Feld-Rendering in separate Komponenten
 * - Klare Trennung von Form-Logik, API-Aufruf und Präsentation
 * - Automatisches Zurücksetzen von Fehlern bei erneuter Eingabe
 *
 * Wichtig zu wissen:
 * Die Komponente verarbeitet Validierungsfehler aus dem Backend als Objekt
 * („errors“) und leitet sie an ValidationErrorDisplay weiter. Durch die Nutzung
 * von TaskFormField wird der Code übersichtlich gehalten und unterstützt eine
 * konsistente, feldgenaue Fehleranzeige bei POST-Requests.
 */
const TaskForm: React.FC = () => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [status, setStatus] = useState('OPEN');
    const [dueDate, setDueDate] = useState('');
    const [assignedTo, setAssignedTo] = useState('');

    const [fieldErrors, setFieldErrors] = useState<{ [key: string]: string }>({});
    const [generalError, setGeneralError] = useState<string | null>(null);
    const [successMessage, setSuccessMessage] = useState<string | null>(null);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [rawErrorDebug, setRawErrorDebug] = useState<any>(null);

    const username = 'user';
    const password = 'password';
    const authHeader = 'Basic ' + btoa(`${username}:${password}`);

    const resetFieldError = (field: string) => {
        if (fieldErrors[field]) {
            setFieldErrors(prev => {
                const updated = { ...prev };
                delete updated[field];
                return updated;
            });
        }
    };

    const handleValidationErrors = (errorData: any) => {
        console.log('🔍 RAW Backend Error Response:', errorData);
        setRawErrorDebug(errorData);

        const errors: { [key: string]: string } = {};

        if (errorData.errors && typeof errorData.errors === 'object' && !Array.isArray(errorData.errors)) {
            Object.entries(errorData.errors).forEach(([field, message]) => {
                if (field && typeof message === 'string') {
                    errors[field] = message;
                }
            });
        } else if (errorData.errors && Array.isArray(errorData.errors)) {
            errorData.errors.forEach((err: any) => {
                const fieldName = err.field || err.objectName || 'unknown';
                const message = err.defaultMessage || err.message || 'Ungültiger Wert';
                if (fieldName !== 'unknown') {
                    errors[fieldName] = message;
                }
            });
        }

        setFieldErrors(errors);

        if (Object.keys(errors).length === 0) {
            const fallbackMsg = errorData.message || errorData.error || 'Validierungsfehler – bitte prüfen Sie die Eingaben.';
            setGeneralError(fallbackMsg);
        } else {
            setGeneralError(null);
        }
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsSubmitting(true);
        setGeneralError(null);
        setSuccessMessage(null);
        setRawErrorDebug(null);

        const taskData = {
            title,
            description,
            status,
            dueDate: dueDate ? new Date(dueDate).toISOString() : undefined,
            assignedTo
        };

        try {
            const response = await fetch('http://localhost:8080/api/tasks', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': authHeader
                },
                body: JSON.stringify(taskData)
            });

            if (response.ok) {
                const createdTask = await response.json();
                setSuccessMessage(`Task "${createdTask.title}" erfolgreich erstellt!`);
                setTitle(''); setDescription(''); setStatus('OPEN'); setDueDate(''); setAssignedTo('');
                setFieldErrors({});
            } else if (response.status === 400) {
                const errorData = await response.json();
                console.log('📡 HTTP 400 Response Body:', errorData);
                handleValidationErrors(errorData);
            } else {
                const errorText = await response.text();
                console.error('❌ Unerwarteter HTTP-Status:', response.status, errorText);
                setGeneralError('Unerwarteter Server-Fehler. Bitte später erneut versuchen.');
            }
        } catch (error) {
            console.error('🚨 Netzwerk- oder JSON-Fehler:', error);
            setGeneralError('Verbindung zum Server fehlgeschlagen. Ist das Backend gestartet?');
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="task-form-container">
            <h2>Neuen Task erstellen</h2>

            <ValidationErrorDisplay
                fieldErrors={fieldErrors}
                rawErrorDebug={rawErrorDebug}
                generalError={generalError}
                successMessage={successMessage}
            />

            <form onSubmit={handleSubmit}>
                <TaskFormField
                    id="title"
                    label="Titel"
                    type="text"
                    value={title}
                    onChange={(e) => { setTitle(e.target.value); resetFieldError('title'); }}
                    error={fieldErrors.title}
                    required
                    disabled={isSubmitting}
                />

                <TaskFormField
                    id="description"
                    label="Beschreibung"
                    type="textarea"
                    value={description}
                    onChange={(e) => { setDescription(e.target.value); resetFieldError('description'); }}
                    error={fieldErrors.description}
                    disabled={isSubmitting}
                />

                <TaskFormField
                    id="status"
                    label="Status"
                    type="select"
                    value={status}
                    onChange={(e) => { setStatus(e.target.value); resetFieldError('status'); }}
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
                    value={dueDate}
                    onChange={(e) => { setDueDate(e.target.value); resetFieldError('dueDate'); }}
                    error={fieldErrors.dueDate}
                    disabled={isSubmitting}
                />

                <TaskFormField
                    id="assignedTo"
                    label="Zugewiesen an"
                    type="text"
                    value={assignedTo}
                    onChange={(e) => { setAssignedTo(e.target.value); resetFieldError('assignedTo'); }}
                    error={fieldErrors.assignedTo}
                    disabled={isSubmitting}
                />

                <button type="submit" disabled={isSubmitting}>
                    {isSubmitting ? 'Wird gespeichert...' : 'Task erstellen'}
                </button>
            </form>
        </div>
    );
};

export default TaskForm;