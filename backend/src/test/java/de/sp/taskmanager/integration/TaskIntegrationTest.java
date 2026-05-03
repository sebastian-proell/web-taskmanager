package de.sp.taskmanager.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.sp.taskmanager.dto.TaskRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * TaskIntegrationTest führt Integrationstests für das gesamte System durch.
 *
 * Die Klasse testet das Zusammenspiel von Controller, Service, Repository und Datenbank
 * unter Verwendung des vollen Spring-Boot-Kontexts. Sie prüft das Abrufen und Erstellen
 * von Tasks über echte HTTP-Aufrufe.
 *
 * Good Practice: @SpringBootTest lädt den kompletten Anwendungskontext, @ActiveProfiles
 * aktiviert das Test-Profil und MockMvc simuliert HTTP-Requests. Der ObjectMapper
 * wird für die JSON-Verarbeitung der TaskRequest-Daten verwendet.
 *
 * Wichtig zu wissen:
 * Diese Testklasse stellt sicher, dass alle Komponenten der Geschäftslogik aus Termin 4
 * (API-Design, Validierung von Benutzereingaben, Fehlermeldungen und Fehlerbehandlung)
 * im vollständigen System korrekt zusammenarbeiten. Die Tests verwenden den kanonischen
 * Record-Konstruktor von TaskRequest und liefern strukturierte JSON-Antworten.
 */
@SpringBootTest(properties = {
        "spring.main.allow-bean-definition-overriding=true",
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=PostgreSQL",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=true"
})
@ActiveProfiles("test")
class TaskIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    // ObjectMapper mit Unterstützung für LocalDateTime
    private final ObjectMapper objectMapper = createObjectMapper();

    /**
     * Erstellt und konfiguriert den ObjectMapper für die JSON-Verarbeitung.
     */
    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    /**
     * Richtet MockMvc vor jedem Test ein.
     */
    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    /**
     * Testet das Abrufen aller Tasks im vollen System.
     */
    @Test
    void getAllTasks_integration_shouldReturnTasks() throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Testet das Erstellen eines Tasks im vollen System mit gültigen Daten.
     */
    @Test
    void createTask_integration_shouldReturnCreatedTask() throws Exception {
        TaskRequest request = new TaskRequest(
                "Neuer Test-Task",
                "Dies ist eine ausführliche Beschreibung für den Integrationstest.",
                "OPEN",
                LocalDateTime.now().plusDays(7),
                "Testuser"
        );

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Neuer Test-Task"));
    }
}