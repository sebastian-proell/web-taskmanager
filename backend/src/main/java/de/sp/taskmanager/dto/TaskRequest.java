package de.sp.taskmanager.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

/**
 * TaskRequest dient als sauberes Data Transfer Object (DTO) für alle Eingaben,
 * die über die REST-API ankommen (Create und Update).
 *
 * Good Practice: DTOs trennen die API-Schnittstelle klar von der Entity.
 * Dadurch bleibt die Entity frei von Validierungs-Annotationen, die nur für
 * die API gelten, und das API-Design wird übersichtlicher und wartbarer.
 *
 * Hier werden jetzt erweiterte Validierungen demonstriert:
 * - @Pattern für erlaubte Werte (z. B. Status als Enum-ähnliche Einschränkung)
 * - @FutureOrPresent + kombinierte Validierungen
 * - @NotNull / @NotBlank mit individuellen Fehlermeldungen
 * - @Size mit präzisen Längenbeschränkungen
 * - Hinweise auf Cross-Field-Validierung und Custom Constraints
 *
 * Wichtig zu wissen:
 * Erweiterte Validierungen (über die Basis-Annotationen hinaus) sind in modernen
 * Spring-Boot-Anwendungen essenziell, um Geschäftsregeln direkt auf API-Ebene
 * durchzusetzen. Mit @Pattern, @FutureOrPresent und der Möglichkeit von Custom
 * Constraints (z. B. eigener Validator für Status-Übergänge) wird die Validierung
 * deklarativ, zentral und wartbar. Das verhindert ungültige Daten schon vor Erreichen
 * der Service-Schicht und liefert klare, feldspezifische Fehlermeldungen an das Frontend.
 */
public record TaskRequest(

        // Basis + erweiterte Validierung: Pflichtfeld mit Längenbeschränkung
        @NotBlank(message = "Der Titel darf nicht leer sein")
        @Size(min = 3, max = 100, message = "Der Titel muss zwischen 3 und 100 Zeichen lang sein")
        String title,

        // Erweiterte Validierung: Beschreibung optional, aber maximal 500 Zeichen
        @Size(max = 500, message = "Die Beschreibung darf maximal 500 Zeichen lang sein")
        String description,

        // ERWEITERTE VALIDIERUNG: Status darf nur bestimmte Werte annehmen (ähnlich Enum)
        // @Pattern erlaubt hier eine reguläre Expression – sehr praktisch für String-basierte Enums
        @NotNull(message = "Der Status muss angegeben werden")
        @Pattern(regexp = "OPEN|IN_PROGRESS|COMPLETED|BLOCKED",
                message = "Ungültiger Status. Erlaubte Werte: OPEN, IN_PROGRESS, COMPLETED, BLOCKED")
        String status,

        // Erweiterte Validierung: Datum muss in der Zukunft oder Gegenwart liegen
        // Zusätzlich könnte man hier mit @AssertTrue eine Cross-Field-Regel ergänzen (siehe unten)
        @FutureOrPresent(message = "Das Fälligkeitsdatum darf nicht in der Vergangenheit liegen")
        LocalDateTime dueDate,

        // Erweiterte Validierung: Zugewiesene Person optional, aber auf sinnvolle Länge beschränkt
        // Beispiel: könnte später mit @Email oder @Pattern für E-Mail-Format erweitert werden
        @Size(max = 50, message = "Der Name des Zuständigen darf maximal 50 Zeichen lang sein")
        String assignedTo

        // BEISPIEL FÜR EINE CROSS-FIELD-VALIDIERUNG (als Kommentar, da Records keine Methoden erlauben):
        // @AssertTrue
        // public boolean isValidDueDateForStatus() {
        //     if ("COMPLETED".equals(status) && dueDate != null && dueDate.isAfter(LocalDateTime.now())) {
        //         return false; // abgeschlossene Tasks dürfen kein zukünftiges Due-Date haben
        //     }
        //     return true;
        // }
        // Hinweis: Für echte Cross-Field-Validierungen in Records empfiehlt sich ein Custom Constraint
        // (z. B. @ValidTaskState) mit eigenem Validator – siehe Best-Practice-Kommentar oben.
) {
        // Record bietet automatisch equals, hashCode, toString und einen kanonischen Konstruktor
}