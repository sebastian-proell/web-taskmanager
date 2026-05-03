package de.sp.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Diese Klasse handhabt globale Exceptions in der Anwendung.
 * Sie fängt Fehler ab und gibt standardisierte Antworten zurück.
 *
 * Good Practice: Ein globaler Exception-Handler sorgt dafür, dass Validierungsfehler,
 * Business-Exceptions und technische Fehler einheitlich und benutzerfreundlich
 * zurückgegeben werden. Die Antwort enthält immer ein konsistentes Fehler-JSON.
 *
 * Wichtig zu wissen: Ein zentraler GlobalExceptionHandler ist eine der wichtigsten
 * Best Practices für saubere Fehlerbehandlung in Spring-Boot-REST-APIs. Er sorgt für konsistente Fehlermeldungen,
 * die sowohl für Entwickler als auch für Frontend-Entwickler leicht interpretierbar sind. Dadurch werden
 * Validierungsfehler automatisch in einem strukturierten JSON zurückgegeben – ohne dass jeder Controller einzeln
 * Fehler behandeln muss.
 */
@RestControllerAdvice
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

    /**
     * Behandelt Validierungsfehler aus @Valid-Annotationen.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("errors", errors);
        response.put("message", "Validierungsfehler – bitte prüfen Sie die Eingaben");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
