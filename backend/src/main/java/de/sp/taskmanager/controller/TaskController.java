package de.sp.taskmanager.controller;

import de.sp.taskmanager.dto.TaskRequest;
import de.sp.taskmanager.dto.TaskResponse;
import de.sp.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Diese Klasse ist ein REST-Controller, der HTTP-Anfragen für Tasks handhabt.
 * Sie verwendet Spring-Annotations wie @RestController und @RequestMapping, um Endpunkte
 * (URLs) zu definieren, die der Client (z.B. ein Browser oder eine App) aufrufen kann.
 *
 * Good Practice: Schichten in der Anwendung werden getrennt (hier: Controller ruft Service auf, der
 * wiederum das Repository nutzt). Das macht den Code modular und leichter testbar. DTOs (Data Transfer Objects)
 * wie TaskRequest und TaskResponse werden verwendet, um nur notwendige Daten über das Netzwerk zu senden
 * und die internen Modelle zu schützen.
 *
 * Wichtig zu wissen: REST steht für Representational State Transfer – es ist ein Stil, APIs zu bauen.
 * @RestController sagt Spring, dass diese Klasse HTTP-Requests verarbeitet und JSON zurückgibt.
 * Controller-Klassen gehören in ein 'controller'-Package für Übersichtlichkeit.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    /**
     * Konstruktor des Controllers. Injiziert den TaskService.
     *
     * Good Practice: Konstruktor-Injection wird immer für Abhängigkeiten verwendet. Das erleichtert
     * Unit-Tests und vermeidet versteckte Fehler.
     *
     * Wichtig zu wissen: Spring erstellt den TaskService automatisch und gibt ihn hier weiter.
     * 'final' sorgt dafür, dass der Service nicht versehentlich geändert wird.
     */
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Holt alle Tasks und gibt sie als Liste zurück.
     * HTTP-Methode: GET /api/tasks
     *
     * Good Practice: Passende HTTP-Status-Codes werden immer zurückgegeben (hier: 200 OK). ResponseEntity
     * wird verwendet, um Flexibilität zu haben (z. B. für Fehlerfälle später).
     *
     * Wichtig zu wissen: @GetMapping definiert einen GET-Endpoint. Der Service übernimmt die Logik,
     * der Controller nur das Routing und die Antwort.
     */
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    /**
     * Holt einen einzelnen Task anhand der ID.
     * HTTP-Methode: GET /api/tasks/{id}
     *
     * Good Practice: PathVariable wird verwendet, um den Wert aus der URL zu übernehmen.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    /**
     * Erstellt einen neuen Task.
     * HTTP-Methode: POST /api/tasks
     *
     * Good Practice: @RequestBody wandelt das JSON des Clients automatisch in ein TaskRequest-Objekt um.
     * Status 201 CREATED wird zurückgegeben.
     */
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    /**
     * Aktualisiert einen bestehenden Task.
     * HTTP-Methode: PUT /api/tasks/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }

    /**
     * Löscht einen Task anhand seiner ID.
     * HTTP-Methode: DELETE /api/tasks/{id}
     *
     * Good Practice: 204 No Content wird zurückgegeben, um zu signalisieren, dass die Operation erfolgreich
     * war, aber nichts zurückgegeben wird. Nicht-Existenz wird im Service gehandhabt (z. B. mit 404).
     *
     * Wichtig zu wissen: DELETE entfernt Ressourcen. Hier wird nur der Service aufgerufen und eine
     * leere Response gebaut.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
