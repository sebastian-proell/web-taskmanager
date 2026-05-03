import React from 'react';

/**
 * ValidationErrorDisplay.tsx ist eine wiederverwendbare Komponente zur
 * zentralen Darstellung von Validierungsfehlern aus dem Backend.
 *
 * Sie kapselt die komplette Fehleranzeige (Debug-Box, Hilfeanzeige, allgemeine
 * Fehlermeldungen und Erfolgsmeldungen) und wird über klare Props gesteuert.
 *
 * Good Practice:
 * - Single Responsibility: Nur für die visuelle Aufbereitung von Fehlern zuständig
 * - Prop-basierte Schnittstelle für feldspezifische Fehler und rohe Server-Daten
 * - Konsistente farbliche Hervorhebung und strukturierte Listen
 * - Inline-Styles für maximale Portabilität ohne externe CSS-Abhängigkeiten
 *
 * Wichtig zu wissen:
 * Die Komponente parst die Server-Antwort (errors als Objekt oder Array) und
 * stellt feldspezifische Validierungsfehler zusammen mit der originalen JSON-
 * Debug-Information übersichtlich dar. Sie ermöglicht eine klare, feldgenaue
 * Rückmeldung an den Benutzer bei HTTP-400-Validierungsfehlern.
 */
interface ValidationErrorDisplayProps {
    fieldErrors: { [key: string]: string };
    rawErrorDebug: any | null;
    generalError: string | null;
    successMessage: string | null;
}

const ValidationErrorDisplay: React.FC<ValidationErrorDisplayProps> = ({
                                                                           fieldErrors,
                                                                           rawErrorDebug,
                                                                           generalError,
                                                                           successMessage
                                                                       }) => {
    return (
        <>
            {/* === DEBUG-BOX – zeigt die originale JSON-Ausgabe des Servers (Lernerfolg) === */}
            {rawErrorDebug && (
                <div style={{
                    backgroundColor: '#f8d7da',
                    border: '3px solid #dc3545',
                    color: '#721c24',
                    padding: '12px',
                    borderRadius: '8px',
                    marginBottom: '20px',
                    fontFamily: 'monospace',
                    fontSize: '0.9em',
                    whiteSpace: 'pre-wrap',
                    maxHeight: '200px',
                    overflowY: 'auto'
                }}>
                    <strong>🔍 DEBUG: Was hat das Backend wirklich zurückgegeben?</strong><br />
                    {JSON.stringify(rawErrorDebug, null, 2)}
                </div>
            )}

            {/* === HILFEANZEIGE mit geparster Server-Antwort === */}
            {Object.keys(fieldErrors).length > 0 && (
                <div style={{
                    backgroundColor: '#fee2e2',
                    border: '3px solid #dc3545',
                    color: '#b71c1c',
                    padding: '16px 20px',
                    borderRadius: '8px',
                    marginBottom: '24px',
                    fontWeight: '600'
                }}>
                    <strong>❌ Hilfeanzeige – Server sagt genau, was falsch ist:</strong>
                    <ul style={{ marginTop: '10px', paddingLeft: '24px', fontWeight: '500' }}>
                        {Object.entries(fieldErrors).map(([field, message]) => (
                            <li key={field} style={{ marginBottom: '6px' }}>
                                • <strong>
                                {field === 'title' ? 'Titel' :
                                    field === 'status' ? 'Status' :
                                        field === 'dueDate' ? 'Fälligkeitsdatum' :
                                            field === 'description' ? 'Beschreibung' :
                                                field === 'assignedTo' ? 'Zugewiesen an' : field}
                            </strong>: {message}
                            </li>
                        ))}
                    </ul>
                    <small style={{ display: 'block', marginTop: '12px', fontWeight: '400' }}>
                        Korrigieren Sie die markierten Felder und versuchen Sie es erneut.
                    </small>
                </div>
            )}

            {generalError && (
                <div style={{
                    backgroundColor: '#fee2e2',
                    border: '3px solid #dc3545',
                    color: '#b71c1c',
                    padding: '16px 20px',
                    borderRadius: '8px',
                    marginBottom: '24px',
                    fontWeight: '600'
                }}>
                    {generalError}
                </div>
            )}

            {successMessage && (
                <div style={{
                    backgroundColor: '#d4edda',
                    color: '#155724',
                    padding: '16px 20px',
                    borderRadius: '8px',
                    marginBottom: '24px',
                    fontWeight: '600'
                }}>
                    {successMessage}
                </div>
            )}
        </>
    );
};

export default ValidationErrorDisplay;