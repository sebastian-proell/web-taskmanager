import React from 'react';

/**
 * TaskFormField.tsx ist eine wiederverwendbare, generische Feld-Komponente
 * für Formulare.
 *
 * Sie kapselt Label, Eingabefeld (Input, Textarea oder Select), automatische
 * Fehlerhervorhebung sowie Warn-Icon und feldspezifische Fehlermeldung.
 *
 * Good Practice:
 * - Single Responsibility: Nur ein einzelnes Formularfeld rendern
 * - Flexible Props für verschiedene Feldtypen und Validierungszustände
 * - Konsistente visuelle Fehlerdarstellung über Inline-Styles
 * - Minimale Duplizierung im aufrufenden Formular
 *
 * Wichtig zu wissen:
 * Die Komponente verarbeitet feldspezifische Validierungsfehler und stellt
 * sie direkt unter dem Eingabefeld dar. Sie unterstützt Text-, Textarea-,
 * Select- und Datetime-Felder und sorgt für eine einheitliche, farblich
 * hervorgehobene Fehleranzeige.
 */
interface TaskFormFieldProps {
    id: string;
    label: string;
    type?: 'text' | 'textarea' | 'select' | 'datetime-local';
    value: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => void;
    error?: string;
    options?: { value: string; label: string }[]; // nur für Select
    required?: boolean;
    disabled?: boolean;
}

const TaskFormField: React.FC<TaskFormFieldProps> = ({
                                                         id,
                                                         label,
                                                         type = 'text',
                                                         value,
                                                         onChange,
                                                         error,
                                                         options,
                                                         required = false,
                                                         disabled = false
                                                     }) => {
    const hasError = !!error;

    return (
        <div className="form-group">
            <label
                htmlFor={id}
                style={hasError ? { color: '#dc3545', fontWeight: '700' } : {}}
            >
                {hasError && <span style={{ marginRight: '6px' }}>⚠️</span>}
                {label}
                {required && ' *'}
            </label>

            {type === 'textarea' ? (
                <textarea
                    id={id}
                    value={value}
                    onChange={onChange}
                    style={hasError ? { borderColor: '#dc3545', boxShadow: '0 0 0 3px rgba(220, 53, 69, 0.25)' } : {}}
                    disabled={disabled}
                />
            ) : type === 'select' && options ? (
                <select
                    id={id}
                    value={value}
                    onChange={onChange}
                    style={hasError ? { borderColor: '#dc3545', boxShadow: '0 0 0 3px rgba(220, 53, 69, 0.25)' } : {}}
                    disabled={disabled}
                >
                    {options.map(opt => (
                        <option key={opt.value} value={opt.value}>
                            {opt.label}
                        </option>
                    ))}
                </select>
            ) : (
                <input
                    id={id}
                    type={type}
                    value={value}
                    onChange={onChange}
                    style={hasError ? { borderColor: '#dc3545', boxShadow: '0 0 0 3px rgba(220, 53, 69, 0.25)' } : {}}
                    disabled={disabled}
                />
            )}

            {error && (
                <span style={{ color: '#dc3545', fontWeight: '600' }}>
                    {error}
                </span>
            )}
        </div>
    );
};

export default TaskFormField;