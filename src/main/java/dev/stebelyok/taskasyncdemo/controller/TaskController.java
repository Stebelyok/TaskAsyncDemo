package dev.stebelyok.taskasyncdemo.controller;

import dev.stebelyok.taskasyncdemo.enumeration.TaskStatus;
import dev.stebelyok.taskasyncdemo.model.Task;
import dev.stebelyok.taskasyncdemo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping("create")
    public ResponseEntity<Task> createTask(@RequestBody String description) {
        return ResponseEntity.ok(taskService.createTask(description));
    }

    @GetMapping("getStatus/{id}")
    public ResponseEntity<TaskStatus> getTaskStatus(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskStatus(id));
    }
}


