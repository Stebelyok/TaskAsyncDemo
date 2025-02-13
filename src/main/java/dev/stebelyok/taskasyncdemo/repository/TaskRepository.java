package dev.stebelyok.taskasyncdemo.repository;

import dev.stebelyok.taskasyncdemo.enumeration.TaskStatus;
import dev.stebelyok.taskasyncdemo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    void deleteByStatus(TaskStatus status);
}
