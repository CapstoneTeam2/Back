package com.neighborcharger.capstoneproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
@Configuration
public class SchedulingConfig implements SchedulingConfigurer {
    private final List<CronTask> scheduledTasks = new ArrayList<>();
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public void scheduleTaskAtTime(String time, Runnable task) {
        CronTask cronTask = new CronTask(task, time);
        scheduledTasks.add(cronTask);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        scheduledTasks.forEach(taskRegistrar::addCronTask);
    }

    private long calculateDelayUntilMidnight() {
        long currentTime = System.currentTimeMillis();
        long midnight = currentTime - (currentTime % (24 * 60 * 60 * 1000)) + (24 * 60 * 60 * 1000);
        return midnight - currentTime;
    }
}
