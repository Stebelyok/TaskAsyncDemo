package dev.stebelyok.taskasyncdemo.monitor;

import dev.stebelyok.taskasyncdemo.service.TaskMonitorService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
@RequiredArgsConstructor
public class TaskMonitor {
    private final TaskMonitorService taskMonitorService;

    @PostConstruct
    public void startMonitoring() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            Map<String, Integer> statistics = taskMonitorService.getTaskStatistics();
            log.info("Task statistics: {}", statistics);
        }, 0, 5, TimeUnit.SECONDS);
    }
}
