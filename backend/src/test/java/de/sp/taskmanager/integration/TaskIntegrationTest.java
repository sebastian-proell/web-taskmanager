package de.sp.taskmanager.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.sp.taskmanager.config.TestSecurityConfig;
import de.sp.taskmanager.dto.TaskRequest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import de.sp.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


/**
 * Diese Klasse enthält Integrationstests für das gesamte System.
 * SpringBootTest lädt den vollen Spring-Kontext inklusive Datenbank und Security.
 *
 * Good Practice: Integrationstests überprüfen das Zusammenspiel aller Schichten (Controller → Service → Repository → Datenbank).
 * @WithMockUser simuliert einen angemeldeten Benutzer.
 *
 * Wichtig zu wissen: @SpringBootTest startet die komplette Anwendung. @AutoConfigureMockMvc ermöglicht
 * HTTP-Tests mit realem Backend. @ActiveProfiles("test") verwendet die Test-Konfiguration und Test-Datenbank.
 */
@SpringBootTest
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
@WithMockUser(username = "testuser", roles = "USER")
class TaskIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Wird vor jedem Test ausgeführt und richtet MockMvc mit Security ein.
     *
     * Good Practice: setup-Methode stellt sicher, dass jeder Test mit derselben Konfiguration startet.
     *
     * Wichtig zu wissen: MockMvcBuilders.webAppContextSetup baut einen MockMvc-Client, der Security berücksichtigt.
     */
    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())  // Explizit Security integrieren
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
     * Testet das Erstellen eines Tasks im vollen System.
     */
    @Test
    void createTask_integration_shouldReturnCreatedTask() throws Exception {
        TaskRequest request = new TaskRequest();
        request.setTitle("Neuer Test-Task");

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Neuer Test-Task"));
    }
}