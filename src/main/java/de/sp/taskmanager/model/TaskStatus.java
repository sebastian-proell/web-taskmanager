package de.sp.taskmanager.model;

/**
 * Repräsentiert den aktuellen Status einer Aufgabe.
 * Die Verwendung eines Enums verhindert ungültige Status-Werte.
 */
public enum TaskStatus {
    OPEN,           // Aufgabe ist neu angelegt
    IN_PROGRESS,    // Aufgabe wird aktuell bearbeitet
    DONE,           // Aufgabe ist abgeschlossen
    CANCELLED       // Aufgabe wurde abgebrochen
}
