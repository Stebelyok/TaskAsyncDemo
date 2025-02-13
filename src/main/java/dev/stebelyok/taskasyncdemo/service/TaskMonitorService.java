package dev.stebelyok.taskasyncdemo.service;

import java.util.Map;

public interface TaskMonitorService {
    void incrementActiveTasks();
    void incrementCompletedTasks();
    void incrementFailedTasks();
    void decrementActiveTasks();
    Map<String, Integer> getTaskStatistics();
}
