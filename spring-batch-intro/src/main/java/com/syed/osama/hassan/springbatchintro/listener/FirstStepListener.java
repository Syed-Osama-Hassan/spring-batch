package com.syed.osama.hassan.springbatchintro.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstStepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Before | Step name: " + stepExecution.getStepName());
        System.out.println("Job Execution Context: " + stepExecution.getJobExecution().getExecutionContext());
        System.out.println("Step Execution Context: " + stepExecution.getExecutionContext());
        stepExecution.getExecutionContext().put("Step Test", "value");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("After | Step name: " + stepExecution.getStepName());
        System.out.println("Job Execution Context: " + stepExecution.getJobExecution().getExecutionContext());
        System.out.println("Step Execution Context: " + stepExecution.getExecutionContext());
        return null;
    }
}
