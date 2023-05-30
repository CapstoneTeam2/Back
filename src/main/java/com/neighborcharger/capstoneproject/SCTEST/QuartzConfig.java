package com.neighborcharger.capstoneproject.SCTEST;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(SimpleJob.class)
                .withIdentity("simpleJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        return TriggerBuilder.newTrigger()
                .forJob(job)
                .withIdentity("simpleTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule())
                .build();
    }
}
