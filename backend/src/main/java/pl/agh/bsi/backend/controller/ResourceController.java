package pl.agh.bsi.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.bsi.backend.controller.dto.Task;
import pl.agh.bsi.backend.service.TaskService;
import pl.agh.bsi.backend.service.UserService;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("resource")
@RequiredArgsConstructor
public class ResourceController {

    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/hello")
    public String getSecuredGreetings(@RequestHeader Map<String, String> headers) {
        if (headers.containsKey("sessionid")) {
            String sessionId = headers.get("sessionid");
            String username = userService.getUsernameForSessionId(sessionId);
            return "Hello " + username + "!";
        }

        return "Hello unauthorized user.";
    }

    @GetMapping("/tasks")
    public ResponseEntity<Object> getUserTasks(@RequestHeader Map<String, String> headers) {
        if(headers.containsKey("sessionid")){
            String username = userService.getLoggedUser(headers.get("sessionid"));

            List<Task> tasks = taskService.getTasksByUser(username);

            if (tasks.isEmpty()) {
                return ResponseEntity.ok("You don't have any tasks");
            }
            return ResponseEntity.ok(tasks);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Wrong sessionId or you're not logged in.");
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Object> getUserTasks(@PathVariable Integer id) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"detail\": \"task not found\"}");
        }
        return ResponseEntity.ok(task);
    }

    @GetMapping("/addTask")
    public ResponseEntity<Object> addUserTask(@RequestHeader Map<String, String> headers,
                                              @RequestParam String title,
                                              @RequestParam String content) {
        if(headers.containsKey("sessionid")){
            String username = userService.getLoggedUser(headers.get("sessionid"));
            taskService.addTaskToUser(username, title, content);
            return ResponseEntity.ok("Task added.");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Wrong sessionId or you're not logged in.");
    }
}