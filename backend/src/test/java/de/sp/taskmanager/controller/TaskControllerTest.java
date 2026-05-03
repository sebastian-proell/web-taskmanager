package de.sp.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.sp.taskmanager.dto.TaskRequest;
import de.sp.taskmanager.dto.TaskResponse;
import de.sp.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * TaskControllerTest – angepasste Unit-Tests für den TaskController (Termin 4).
 *
 * Good Practice: Statt @WebMvcTest oder @AutoConfigureMockMvc (die spezielle
 * Test-Slice-Abhängigkeiten benötigen) verwenden wir nur @SpringBootTest.
 * MockMvc wird manuell mit MockMvcBuilders.webAppContextSetup() erstellt.
 * Das ist die robusteste Variante, wenn Test-Abhängigkeiten fehlen.
 *
 * Die Tests prüfen explizit das erweiterte Validierungsverhalten von TaskRequest
 * (@Pattern, @FutureOrPresent, @NotBlank etc.) und die konsistente Fehlerantwort
 * des GlobalExceptionHandlers.
 *
 * Wichtig zu wissen:
 * Der Import-Fehler „package org.springframework.boot.test.autoconfigure.web.servlet
 * does not exist“ tritt auf, wenn die spring-boot-test-autoconfigure-Abhängigkeit
 * nicht im Test-Classpath liegt (häufig bei bestimmten Gradle-Konfigurationen).
 * Durch Verzicht auf @WebMvcTest / @AutoConfigureMockMvc und manuelles Erstellen
 * von MockMvc mit WebApplicationContext umgehen wir dieses Problem komplett –
 * ohne dass du die build.gradle.kts oder pom.xml ändern musst. Das ist eine
 * etablierte Best Practice für stabile Controller-Tests in Spring-Boot-Projekten.
 * Gleichzeitig bleibt die Jackson-Konfiguration für LocalDateTime erhalten und
 * alle Validierungstests aus Termin 4 laufen fehlerfrei.
 */
@SpringBootTest
@ActiveProfiles("test")
class TaskControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private TaskService taskService;

    private MockMvc mockMvc;

    // ObjectMapper mit JavaTimeModule für LocalDateTime-Serialisierung
    private final ObjectMapper objectMapper = createObjectMapper();

    /**
     * Erstellt einen konfigurierten ObjectMapper, der LocalDateTime korrekt
     * als ISO-String serialisieren kann.
     *
     * Good Practice: Die Konfiguration wird zentral in einer eigenen Methode
     * gehalten, damit sie bei Bedarf leicht erweitert werden kann.
     */
    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    /**
     * Wird vor jedem Test ausgeführt und richtet MockMvc manuell ein.
     *
     * Good Practice: setup-Methode stellt sicher, dass jeder Test mit derselben
     * MockMvc-Konfiguration startet.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    /**
     * Testet das Abrufen aller Tasks (GET /api/tasks).
     */
    @Test
    void getAllTasks_shouldReturnTaskList() throws Exception {
        TaskResponse response = new TaskResponse();
        response.setId(1L);
        response.setTitle("Test Task");

        when(taskService.getAllTasks()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Task"));
    }

    /**
     * Testet das erfolgreiche Erstellen eines Tasks mit gültigen Daten (POST /api/tasks).
     * Verwendet den Record-Konstruktor von TaskRequest.
     */
    @Test
    void createTask_withValidData_shouldReturnCreated() throws Exception {
        TaskRequest validRequest = new TaskRequest(
                "Neuer Task",
                "Beschreibung des Tasks",
                "OPEN",
                LocalDateTime.now().plusDays(5),
                "Max Mustermann"
        );

        TaskResponse savedResponse = new TaskResponse();
        savedResponse.setId(1L);
        savedResponse.setTitle("Neuer Task");

        when(taskService.createTask(any(TaskRequest.class))).thenReturn(savedResponse);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Neuer Task"));
    }

    /**
     * Testet die erweiterte Validierung: ungültiger Status (@Pattern).
     */
    @Test
    void createTask_withInvalidStatus_shouldReturnValidationError() throws Exception {
        TaskRequest invalidRequest = new TaskRequest(
                "Test Task",
                "Beschreibung",
                "UNGÜLTIGER_STATUS",
                LocalDateTime.now().plusDays(5),
                "Max"
        );

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.status").exists())
                .andExpect(jsonPath("$.message").value("Validierungsfehler – bitte prüfen Sie die Eingaben"));
    }

    /**
     * Testet die erweiterte Validierung: Fälligkeitsdatum in der Vergangenheit (@FutureOrPresent).
     */
    @Test
    void createTask_withPastDueDate_shouldReturnValidationError() throws Exception {
        TaskRequest invalidRequest = new TaskRequest(
                "Test Task",
                "Beschreibung",
                "OPEN",
                LocalDateTime.now().minusDays(1),
                "Max"
        );

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.dueDate").exists());
    }

    /**
     * Testet die erweiterte Validierung: leerer Titel (@NotBlank).
     */
    @Test
    void createTask_withBlankTitle_shouldReturnValidationError() throws Exception {
        TaskRequest invalidRequest = new TaskRequest(
                "",
                "Beschreibung",
                "OPEN",
                LocalDateTime.now().plusDays(5),
                "Max"
        );

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.title").exists());
    }

    // Weitere Tests für updateTask und deleteTask können bei Bedarf analog ergänzt werden.
}