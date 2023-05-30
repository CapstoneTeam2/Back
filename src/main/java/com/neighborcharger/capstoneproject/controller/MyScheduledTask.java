package com.neighborcharger.capstoneproject.controller;

import com.neighborcharger.capstoneproject.config.SchedulingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyScheduledTask {

    private final SchedulingConfig schedulingConfig;

    public MyScheduledTask(SchedulingConfig schedulingConfig) {
        this.schedulingConfig = schedulingConfig;
    }

    public void scheduleFunctionAtTime(String time) {
        schedulingConfig.scheduleTaskAtTime(time, this::myFunction);
    }

    private void myFunction() {
        System.out.println("샘플입니다 실행되나요?");
    }
}