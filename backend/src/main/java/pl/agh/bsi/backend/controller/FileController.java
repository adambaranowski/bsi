package pl.agh.bsi.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

@RestController
public class FileController {
    @GetMapping("/file/{filename}")
    public String getFileContent(@PathVariable String filename) throws Exception {
        Path path = Paths.get("src/main/resources/text_files/"+filename+".txt");
        return new String(Files.readAllBytes(path));
    }
}