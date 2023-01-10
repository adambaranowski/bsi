package pl.agh.bsi.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.bsi.backend.service.ConfigService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/config")
public class ConfigController {

    private final ConfigService configService;


    @GetMapping("/reset")
    public ResponseEntity<Object> resetDatabase(){
        configService.resetDatabase();
        return ResponseEntity.ok().build();
    }
}
