const API_BASE_URL = 'http://localhost:8080/api/tasks';

function getAuthHeader(): string {
    const username = import.meta.env.VITE_API_USERNAME || 'user';
    const password = import.meta.env.VITE_API_PASSWORD || 'password';
    return `Basic ${btoa(`${username}:${password}`)}`;
}

export interface Task {
    id: number;
    title: string;
    description: string;
    status: string;
    dueDate: string;
    assignedTo: string;
}

export interface TaskRequest {
    title: string;
    description?: string;
    status: string;
    dueDate?: string;
    assignedTo?: string;
}

/**
 * taskService – Zentrale Schicht für alle API-Aufrufe rund um Tasks.
 *
 * Diese Datei kapselt die gesamte Kommunikation mit dem Backend.
 * Komponenten und Hooks sollten idealerweise nie direkt fetch() aufrufen.
 *
 * Good Practice:
 * - Single Responsibility: Nur für HTTP-Kommunikation zuständig
 * - Zentrale Fehlerbehandlung und Authentifizierung
 * - Einfache, typsichere Schnittstelle für die restliche Anwendung
 * - Leicht austauschbar (z. B. später gegen TanStack Query oder Axios)
 *
 * Wichtig zu wissen:
 * Der Service stellt sicher, dass Authentifizierung, Base-URL und
 * Fehlerbehandlung an einer Stelle gepflegt werden. Dadurch bleibt
 * der Code in Hooks und Komponenten schlank und lesbar.
 */
export const taskService = {
    async getAll(): Promise<Task[]> {
        const response = await fetch(API_BASE_URL, {
            method: 'GET',
            headers: {
                'Authorization': getAuthHeader(),
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP ${response.status} - ${response.statusText}`);
        }

        return response.json();
    },

    async create(task: TaskRequest): Promise<Task> {
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': getAuthHeader()
            },
            body: JSON.stringify(task)
        });

        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            throw { status: response.status, data: errorData };
        }

        return response.json();
    },

    async update(id: number, task: TaskRequest): Promise<Task> {
        const response = await fetch(`${API_BASE_URL}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': getAuthHeader()
            },
            body: JSON.stringify(task)
        });

        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            throw { status: response.status, data: errorData };
        }

        return response.json();
    },

    async delete(id: number): Promise<void> {
        const response = await fetch(`${API_BASE_URL}/${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': getAuthHeader()
            }
        });

        if (!response.ok) {
            throw new Error(`Delete failed with status ${response.status}`);
        }
    }
};