package de.sp.taskmanager.controller;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;   // NEU in SB 4
import org.springframework.test.context.bean.override.mockito.MockitoBean;  // NEU statt @MockBean

// Rest bleibt gleich
import com.fasterxml.jackson.databind.ObjectMapper;
import de.sp.taskmanager.dto.TaskRequest;
import de.sp.taskmanager.dto.TaskResponse;
import de.sp.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Diese Klasse enthält Unit-Tests für den TaskController mit MockMvc.
 * Es wird nur die Web-Schicht (Controller) getestet, der Service wird gemockt.
 *
 * Good Practice: @WebMvcTest lädt nur die Controller-Schicht und die notwendigen Web-Konfigurationen.
 * Das macht die Tests sehr schnell und isoliert.
 *
 * Wichtig zu wissen: Unit-Tests testen einzelne Klassen isoliert. MockitoBean ersetzt den echten
 * TaskService durch einen simulierten (Mock). MockMvc simuliert HTTP-Aufrufe ohne echten Server.
 */
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Testet das Abrufen aller Tasks (GET /api/tasks).
     *
     * Good Practice: Jeder Test hat einen klaren Namen, der beschreibt, was getestet wird und welches Ergebnis erwartet wird.
     * Assertions prüfen Status-Code, Content-Type und JSON-Inhalt.
     *
     * Wichtig zu wissen: when(...).thenReturn(...) legt fest, was der gemockte Service zurückgeben soll.
     * jsonPath prüft den Inhalt der JSON-Antwort.
     */
    @Test
    void getAllTasks_shouldReturnTaskList() throws Exception {
        TaskResponse response = new TaskResponse();
        response.setId(1L);
        response.setTitle("Test");

        when(taskService.getAllTasks()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test"));
    }

    /**
     * Testet das Erstellen eines Tasks (POST /api/tasks).
     *
     * Good Practice: Auch das Erstellen wird mit einem Request-Body und dem erwarteten Status 201 getestet.
     */
    @Test
    void createTask_shouldReturnCreatedTask() throws Exception {
        TaskRequest request = new TaskRequest();
        request.setTitle("New Task");

        TaskResponse response = new TaskResponse();
        response.setId(1L);
        response.setTitle("New Task");

        when(taskService.createTask(any(TaskRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Task"));
    }

    // Weitere Tests für getById, update und delete können analog hinzugefügt werden.
}
