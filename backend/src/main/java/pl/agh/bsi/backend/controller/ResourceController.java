package pl.agh.bsi.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.bsi.backend.service.UserService;

import java.util.Map;

@RestController()
@RequestMapping("resource")
@RequiredArgsConstructor
public class ResourceController {
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
}
