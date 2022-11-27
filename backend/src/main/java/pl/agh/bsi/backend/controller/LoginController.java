package pl.agh.bsi.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.bsi.backend.controller.dto.LoginResponseDto;
import pl.agh.bsi.backend.service.UserService;

import javax.security.auth.login.CredentialException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestParam String username, @RequestParam String password){
        try {
            String sessionId = userService.logUser(username, password);
            return  ResponseEntity.ok(new LoginResponseDto(sessionId));
        } catch (CredentialException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("WRONG USERNAME OR PASSWORD");
        }

    }

    @GetMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestParam String username, @RequestParam String password){
        userService.registerUser(username, password);
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logoutUser(@RequestHeader Map<String, String> headers){
        if(headers.containsKey("sessionid")){
           userService.logOutUser(headers.get("sessionid"));
        }
    }
}
