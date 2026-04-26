package de.sp.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Diese Klasse enthält einen grundlegenden Smoke-Test für die gesamte Anwendung.
 *
 * Good Practice: Der contextLoads-Test prüft, ob der Spring Application Context fehlerfrei startet.
 * Das ist der erste Test, der ausgeführt werden sollte.
 *
 * Wichtig zu wissen: @SpringBootTest lädt den kompletten Kontext (inklusive Datenbank und Security).
 * Wenn dieser Test grün läuft, ist die grundlegende Konfiguration korrekt.
 */
@SpringBootTest
@ActiveProfiles("test")
class TaskManagerApplicationTests {

    /**
     * Dieser Test überprüft, ob der Spring Application Context erfolgreich lädt.
     *
     * Good Practice: Starte immer mit einem einfachen Kontext-Test, bevor detailliertere Tests geschrieben werden.
     */
    @Test
    void contextLoads() {
        // Wenn dieser Test grün läuft, ist der gesamte Kontext (inkl. DB + Security) ok
    }
}
