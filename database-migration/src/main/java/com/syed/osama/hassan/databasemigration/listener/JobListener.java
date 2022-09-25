package com.syed.osama.hassan.databasemigration.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("PROCESSING STARTED | " + new Date(System.currentTimeMillis()));
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("PROCESSING END | TOTAL_TIME: " + new Date(System.currentTimeMillis()));
    }
}
