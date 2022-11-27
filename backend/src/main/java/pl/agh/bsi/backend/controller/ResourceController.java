package pl.agh.bsi.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("resource")
public class ResourceController {

    @GetMapping("/hello")
    public String getSecuredGreetings(){
        return "Hello";
    }
}
