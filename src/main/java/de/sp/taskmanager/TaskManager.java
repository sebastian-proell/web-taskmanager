package de.sp.taskmanager;

import de.sp.taskmanager.model.Task;
import de.sp.taskmanager.model.TaskPriority;

import java.time.LocalDate;

/**
 * Demo-Klasse für Termin 1.
 * Diese Klasse zeigt, wie du die Task-Klasse verwenden und testen kannst.
 */
public class TaskManager {

    public static void main(String[] args) {
        System.out.println("=== TaskManager - Termin 1 ===");
        System.out.println("Demonstration des objektorientierten Modells\n");

        Task task1 = new Task(
                "Login-Funktion mit OAuth2",
                "Integration von Azure AD in die Web-Anwendung",
                TaskPriority.HIGH,
                LocalDate.of(2026, 4, 20),
                "Max Mustermann"
        );

        Task task2 = new Task(
                "Datenbank optimieren",
                "Indizes auf der Task-Tabelle anlegen",
                TaskPriority.MEDIUM,
                LocalDate.of(2026, 4, 10),
                "Anna Schmidt"
        );



        System.out.println(task1);
        System.out.println("Überfällig? " + task1.isOverdue());

        System.out.println("\n" + task2);
        System.out.println("Überfällig? " + task2.isOverdue());
    }
}
