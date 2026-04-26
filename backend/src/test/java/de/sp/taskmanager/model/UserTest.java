package de.sp.taskmanager.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testklasse für die User-Entity.
 */
class UserTest {

    @Test
    @DisplayName("User sollte Name und E-Mail korrekt setzen")
    void shouldSetNameAndEmailCorrectly() {
        User user = new User("Max Mustermann", "max.mustermann@example.com");

        assertEquals("Max Mustermann", user.getName());
        assertEquals("max.mustermann@example.com", user.getEmail());
    }

    @Test
    @DisplayName("toString() sollte lesbare Ausgabe liefern")
    void shouldReturnReadableToString() {
        User user = new User("Anna Schmidt", "anna.schmidt@example.com");
        String result = user.toString();

        assertTrue(result.contains("Anna Schmidt"));
        assertTrue(result.contains("anna.schmidt@example.com"));
    }
}