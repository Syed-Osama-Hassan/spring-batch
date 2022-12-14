package com.syed.osama.hassan.springbatchintro.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Before | Job name: " + jobExecution.getJobInstance().getJobName());
        System.out.println("Job parameters: " + jobExecution.getJobParameters());
        System.out.println("Job Execution Context: " + jobExecution.getExecutionContext());
        jobExecution.getExecutionContext().put("Test", "value");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("After | Job name: " + jobExecution.getJobInstance().getJobName());
        System.out.println("Job parameters: " + jobExecution.getJobParameters());
        System.out.println("Job Execution Context: " + jobExecution.getExecutionContext());
    }

}
