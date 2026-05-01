package de.sp.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * HAUPT-EINSTIEGSPUNKT DER GESAMTEN ANWENDUNG
 *
 * Diese Klasse startet die komplette Spring-Boot-Anwendung. Sie ist der zentrale Ausgangspunkt,
 * von dem aus der Webserver, die Datenbankverbindung, die Security und alle anderen Komponenten gestartet werden.
 *
 * Good Practice: Jede Spring-Boot-Anwendung enthält genau eine Klasse mit der Annotation @SpringBootApplication.
 * Dies ermöglicht die automatische Konfiguration und den Komponenten-Scan.
 *
 * Wichtig zu wissen: Die main-Methode ist der Einstiegspunkt, den Java beim Start aufruft.
 * SpringApplication.run() startet den eingebetteten Tomcat-Webserver und lädt alle Konfigurationen.
 */
@SpringBootApplication
public class TaskManagerApplication {

    /**
     * Die main-Methode startet die Spring-Boot-Anwendung.
     *
     * Good Practice: Argumente (args) werden an Spring weitergegeben, sodass man z. B. Profile oder
     * Port-Einstellungen über die Kommandozeile setzen kann.
     *
     * Wichtig zu wissen: SpringApplication.run() ist eine statische Methode von Spring Boot,
     * die den gesamten Lebenszyklus der Anwendung steuert.
     */
    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }
}
