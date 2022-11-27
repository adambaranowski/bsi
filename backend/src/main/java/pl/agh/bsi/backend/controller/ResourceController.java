package pl.agh.bsi.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<Object> getUserTasks(@RequestHeader Map<String, String> headers){
        if(headers.containsKey("sessionid")){
            List<Task> tasks = taskService.getTasksByUser(
                    userService.getLoggedUser(headers.get("sessionid")));

            if (tasks.isEmpty()) {
                return ResponseEntity.ok("You don't have any tasks");
            }
            return ResponseEntity.ok(tasks);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("YOU'RE NOT LOGGED IN");
    }
}