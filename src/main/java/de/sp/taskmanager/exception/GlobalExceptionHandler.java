package de.sp.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Diese Klasse handhabt globale Exceptions in der Anwendung.
 * Sie fängt Fehler ab und gibt standardisierte Antworten zurück.
 *
 * Good Practice: Zentrale Exception-Handling vermeidet duplizierten Code in Controllern.
 * @ControllerAdvice macht dies für alle Controller verfügbar.
 *
 * Wichtig zu wissen: Exceptions sind Fehler, die während der Ausführung auftreten. Hier wird RuntimeException
 * gefangen und mit HTTP 404 beantwortet.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handhabt RuntimeExceptions und gibt eine 404-Antwort zurück.
     *
     * Good Practice: Immer den passenden HTTP-Status wählen (z. B. 404 für "nicht gefunden").
     * Die Fehlermeldung wird im Body gesendet.
     *
     * Wichtig zu wissen: @ExceptionHandler spezifiziert, welche Exception behandelt wird.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
