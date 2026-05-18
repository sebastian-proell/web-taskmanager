import { useReducer, useEffect } from 'react';
import { taskService, Task } from '../services/taskService';

type TasksState = {
    tasks: Task[];
    loading: boolean;
    error: string | null;
    editingTask: Task | null;
};

type TasksAction =
    | { type: 'SET_TASKS'; payload: Task[] }
    | { type: 'SET_LOADING'; payload: boolean }
    | { type: 'SET_ERROR'; payload: string | null }
    | { type: 'SET_EDITING_TASK'; payload: Task | null }
    | { type: 'DELETE_TASK'; payload: number };

const initialState: TasksState = {
    tasks: [],
    loading: true,
    error: null,
    editingTask: null
};

function tasksReducer(state: TasksState, action: TasksAction): TasksState {
    switch (action.type) {
        case 'SET_TASKS':
            return { ...state, tasks: action.payload, loading: false, error: null };
        case 'SET_LOADING':
            return { ...state, loading: action.payload };
        case 'SET_ERROR':
            return { ...state, error: action.payload, loading: false };
        case 'SET_EDITING_TASK':
            return { ...state, editingTask: action.payload };
        case 'DELETE_TASK':
            return {
                ...state,
                tasks: state.tasks.filter(t => t.id !== action.payload)
            };
        default:
            return state;
    }
}

/**
 * useTasks Hook – Zentrale Zustandsverwaltung für die Task-Liste.
 *
 * Dieser Hook kapselt Laden, Löschen und die Auswahl zum Bearbeiten.
 * Er verwendet useReducer, um einen konsistenten Gesamtzustand zu gewährleisten.
 *
 * Good Practice:
 * - Zentrale State-Logik für die Liste (Single Source of Truth)
 * - useReducer für klare Zustandsübergänge
 * - Trennung von Datenbeschaffung (Service) und UI-State
 * - Einfache Schnittstelle für Komponenten
 *
 * Wichtig zu wissen:
 * Der Hook stellt sicher, dass Lade-, Fehler- und Bearbeitungszustände
 * konsistent verwaltet werden. Komponenten müssen sich nicht mehr um
 * das Koordinieren mehrerer useState-Hooks kümmern.
 */
export function useTasks() {
    const [state, dispatch] = useReducer(tasksReducer, initialState);

    const loadTasks = async () => {
        dispatch({ type: 'SET_LOADING', payload: true });
        dispatch({ type: 'SET_ERROR', payload: null });

        try {
            const data = await taskService.getAll();
            dispatch({ type: 'SET_TASKS', payload: data });
        } catch (err: any) {
            dispatch({ type: 'SET_ERROR', payload: err.message || 'Fehler beim Laden der Aufgaben' });
        }
    };

    const deleteTask = async (id: number) => {
        if (!window.confirm('Task wirklich löschen?')) return;

        try {
            await taskService.delete(id);
            dispatch({ type: 'DELETE_TASK', payload: id });
        } catch (err) {
            console.error('Fehler beim Löschen:', err);
        }
    };

    const startEditing = (task: Task) => {
        dispatch({ type: 'SET_EDITING_TASK', payload: task });
    };

    const cancelEditing = () => {
        dispatch({ type: 'SET_EDITING_TASK', payload: null });
    };

    const refreshAfterSave = async () => {
        dispatch({ type: 'SET_EDITING_TASK', payload: null });
        await loadTasks();
    };

    useEffect(() => {
        loadTasks();
    }, []);

    return {
        tasks: state.tasks,
        loading: state.loading,
        error: state.error,
        editingTask: state.editingTask,
        loadTasks,
        deleteTask,
        startEditing,
        cancelEditing,
        refreshAfterSave
    };
}