package dev.stebelyok.taskasyncdemo.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TaskMonitorServiceImpl implements TaskMonitorService {
    private final AtomicInteger activeTasks = new AtomicInteger(0);
    private final AtomicInteger completedTasks = new AtomicInteger(0);
    private final AtomicInteger failedTasks = new AtomicInteger(0);


    @Override
    public void incrementActiveTasks() {
        activeTasks.incrementAndGet();
    }

    @Override
    public void incrementCompletedTasks() {
        completedTasks.incrementAndGet();
    }

    @Override
    public void incrementFailedTasks() {
        failedTasks.incrementAndGet();
    }

    @Override
    public void decrementActiveTasks() {
        activeTasks.decrementAndGet();
    }

    @Override
    public Map<String, Integer> getTaskStatistics() {
        Map<String, Integer> statistics = new HashMap<>();
        statistics.put("active", activeTasks.get());
        statistics.put("completed", completedTasks.get());
        statistics.put("failed", failedTasks.get());
        return statistics;
    }
}
