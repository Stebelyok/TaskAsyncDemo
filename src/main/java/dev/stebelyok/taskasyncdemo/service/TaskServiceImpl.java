package dev.stebelyok.taskasyncdemo.service;

import dev.stebelyok.taskasyncdemo.enumeration.TaskStatus;
import dev.stebelyok.taskasyncdemo.exception.EntityNotFoundException;
import dev.stebelyok.taskasyncdemo.exception.ErrorMessages;
import dev.stebelyok.taskasyncdemo.model.Task;
import dev.stebelyok.taskasyncdemo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMonitorService taskMonitorService;

    @Async
    @Override
    public void processTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.TASK_NOT_FOUND, taskId)));
        task.setStatus(TaskStatus.IN_PROGRESS);
        taskRepository.save(task);

        taskMonitorService.incrementActiveTasks();

        try {
            Thread.sleep(10000);
            task.setStatus(TaskStatus.COMPLETED);
            task.setResult("Processed task successfully");
            taskMonitorService.incrementCompletedTasks();
        } catch (InterruptedException e) {
            task.setStatus(TaskStatus.FAILED);
            task.setResult("Processing task failed");
            taskMonitorService.incrementFailedTasks();
        } finally {
            taskMonitorService.decrementActiveTasks();
        }

        taskRepository.save(task);

        sendNotification(task);
    }

    @Override
    public Task createTask(String description) {
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(TaskStatus.CREATED);
        task = taskRepository.save(task);

        processTask(task.getId());

        return task;
    }

    @Override
    public TaskStatus getTaskStatus(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.TASK_NOT_FOUND, taskId)));

        return task.getStatus();
    }

    @Scheduled(fixedRate = 60000)
    public void cleanupOldTasks() {
        taskRepository.deleteByStatus(TaskStatus.COMPLETED);
    }

    private void sendNotification(Task task) {
        log.info("Notification: Task {} is {}", task.getId(), task.getStatus());
    }
}
