package pl.agh.bsi.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.agh.bsi.backend.controller.dto.Task;
import pl.agh.bsi.backend.repository.TaskRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getTasksByUser(String username) {
        return taskRepository.getTasksByOwner(username);
    }

    public Task getTaskById(Integer id) {
        return taskRepository.getTaskById(id);
    }

    public void addTaskToUser(String owner, String title, String content) {
        taskRepository.addTaskByOwnerTitleContent(owner, title, content);
    }
}
