package de.sp.taskmanager.model;

/**
 * Dieses Enum definiert mögliche Status für ein Task.
 * Enums sind konstante Werte, die Tippfehler vermeiden.
 *
 * Good Practice: Enums für festgelegte Werte verwenden, anstatt Strings, um Konsistenz zu gewährleisten.
 * In Entities als @Enumerated speichern.
 *
 * Wichtig zu wissen: Enums sind wie eine Liste vordefinierter Optionen, z. B. für Status-Änderungen.
 */
public enum TaskStatus {
    OPEN,
    IN_PROGRESS,
    COMPLETED,
    BLOCKED
}
