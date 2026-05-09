import { useReducer, useEffect } from 'react';
import { taskService, Task, TaskRequest } from '../services/taskService';

type FormState = {
    formData: {
        title: string;
        description: string;
        status: string;
        dueDate: string;
        assignedTo: string;
    };
    fieldErrors: { [key: string]: string };
    generalError: string | null;
    successMessage: string | null;
    isSubmitting: boolean;
    isEditing: boolean;
    rawErrorDebug: any;
};

type FormAction =
    | { type: 'SET_FORM_DATA'; payload: Partial<FormState['formData']> }
    | { type: 'SET_FIELD_ERROR'; payload: { field: string; message: string } }
    | { type: 'CLEAR_FIELD_ERROR'; payload: string }
    | { type: 'SET_ERRORS'; payload: { [key: string]: string } }
    | { type: 'SET_GENERAL_ERROR'; payload: string | null }
    | { type: 'SET_SUCCESS'; payload: string | null }
    | { type: 'SET_SUBMITTING'; payload: boolean }
    | { type: 'SET_EDITING'; payload: boolean }
    | { type: 'SET_RAW_ERROR'; payload: any }
    | { type: 'RESET_FORM' };

const initialState: FormState = {
    formData: {
        title: '',
        description: '',
        status: 'OPEN',
        dueDate: '',
        assignedTo: ''
    },
    fieldErrors: {},
    generalError: null,
    successMessage: null,
    isSubmitting: false,
    isEditing: false,
    rawErrorDebug: null
};

function formReducer(state: FormState, action: FormAction): FormState {
    switch (action.type) {
        case 'SET_FORM_DATA':
            return {
                ...state,
                formData: { ...state.formData, ...action.payload }
            };
        case 'SET_FIELD_ERROR':
            return {
                ...state,
                fieldErrors: { ...state.fieldErrors, [action.payload.field]: action.payload.message }
            };
        case 'CLEAR_FIELD_ERROR':
            const { [action.payload]: _, ...rest } = state.fieldErrors;
            return { ...state, fieldErrors: rest };
        case 'SET_ERRORS':
            return { ...state, fieldErrors: action.payload };
        case 'SET_GENERAL_ERROR':
            return { ...state, generalError: action.payload };
        case 'SET_SUCCESS':
            return { ...state, successMessage: action.payload };
        case 'SET_SUBMITTING':
            return { ...state, isSubmitting: action.payload };
        case 'SET_EDITING':
            return { ...state, isEditing: action.payload };
        case 'SET_RAW_ERROR':
            return { ...state, rawErrorDebug: action.payload };
        case 'RESET_FORM':
            return {
                ...initialState,
                formData: { ...initialState.formData }
            };
        default:
            return state;
    }
}

/**
 * useTaskForm Hook – Kapselt die gesamte Formularlogik inklusive Zustandsmaschine.
 *
 * Der Hook verwendet useReducer, um einen konsistenten Request-Lebenszyklus
 * (idle → submitting → success / error) zu gewährleisten.
 *
 * Good Practice:
 * - Verwendung von useReducer für komplexe, voneinander abhängige Zustände
 * - Explizite Zustandsübergänge verhindern inkonsistente UI-Zustände
 * - Trennung von State-Logik und UI durch Custom Hook + Reducer
 * - Wiederverwendbarkeit und gute Testbarkeit
 *
 * Wichtig zu wissen:
 * Durch den Reducer werden alle relevanten Zustände (Formulardaten, Fehler,
 * Loading, Success) zentral und konsistent verwaltet. Jede Aktion führt zu
 * einem definierten neuen Gesamtzustand. Dies ist besonders bei Formularen
 * mit Validierung und asynchronen Operationen von Vorteil.
 */
export function useTaskForm(task?: Task | null, onSaved?: () => void) {
    const [state, dispatch] = useReducer(formReducer, initialState);

    const resetForm = () => {
        dispatch({ type: 'RESET_FORM' });
    };

    useEffect(() => {
        if (task) {
            dispatch({
                type: 'SET_FORM_DATA',
                payload: {
                    title: task.title || '',
                    description: task.description || '',
                    status: task.status || 'OPEN',
                    dueDate: task.dueDate ? new Date(task.dueDate).toISOString().slice(0, 16) : '',
                    assignedTo: task.assignedTo || ''
                }
            });
            dispatch({ type: 'SET_EDITING', payload: true });
            dispatch({ type: 'SET_SUCCESS', payload: null });
            dispatch({ type: 'SET_GENERAL_ERROR', payload: null });
            dispatch({ type: 'SET_ERRORS', payload: {} });
        } else {
            resetForm();
        }
    }, [task]);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        dispatch({ type: 'SET_FORM_DATA', payload: { [name]: value } });
        dispatch({ type: 'CLEAR_FIELD_ERROR', payload: name });
    };

    const handleValidationErrors = (errorData: any) => {
        dispatch({ type: 'SET_RAW_ERROR', payload: errorData });

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

        dispatch({ type: 'SET_ERRORS', payload: errors });

        if (Object.keys(errors).length === 0) {
            const fallbackMsg = errorData.message || errorData.error || 'Validierungsfehler – bitte prüfen Sie die Eingaben.';
            dispatch({ type: 'SET_GENERAL_ERROR', payload: fallbackMsg });
        } else {
            dispatch({ type: 'SET_GENERAL_ERROR', payload: null });
        }
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        dispatch({ type: 'SET_SUBMITTING', payload: true });
        dispatch({ type: 'SET_GENERAL_ERROR', payload: null });
        dispatch({ type: 'SET_SUCCESS', payload: null });
        dispatch({ type: 'SET_RAW_ERROR', payload: null });

        const taskData: TaskRequest = {
            title: state.formData.title,
            description: state.formData.description || undefined,
            status: state.formData.status,
            dueDate: state.formData.dueDate ? new Date(state.formData.dueDate).toISOString() : undefined,
            assignedTo: state.formData.assignedTo || undefined
        };

        try {
            let savedTask: Task;

            if (state.isEditing && task) {
                savedTask = await taskService.update(task.id, taskData);
                dispatch({ type: 'SET_SUCCESS', payload: `Task "${savedTask.title}" erfolgreich aktualisiert!` });
            } else {
                savedTask = await taskService.create(taskData);
                dispatch({ type: 'SET_SUCCESS', payload: `Task "${savedTask.title}" erfolgreich erstellt!` });
                resetForm();
            }

            if (onSaved) onSaved();
        } catch (err: any) {
            if (err.status === 400 && err.data) {
                handleValidationErrors(err.data);
            } else {
                dispatch({ type: 'SET_GENERAL_ERROR', payload: 'Verbindung zum Server fehlgeschlagen oder unerwarteter Fehler.' });
            }
        } finally {
            dispatch({ type: 'SET_SUBMITTING', payload: false });
        }
    };

    return {
        formData: state.formData,
        fieldErrors: state.fieldErrors,
        generalError: state.generalError,
        successMessage: state.successMessage,
        isSubmitting: state.isSubmitting,
        isEditing: state.isEditing,
        rawErrorDebug: state.rawErrorDebug,
        handleInputChange,
        handleSubmit,
        resetForm
    };
}