package dev.stebelyok.taskasyncdemo.service;

import dev.stebelyok.taskasyncdemo.enumeration.TaskStatus;
import dev.stebelyok.taskasyncdemo.model.Task;

public interface TaskService {
    void processTask(Long taskId);

    Task createTask(String description);

    TaskStatus getTaskStatus(Long taskId);
}
