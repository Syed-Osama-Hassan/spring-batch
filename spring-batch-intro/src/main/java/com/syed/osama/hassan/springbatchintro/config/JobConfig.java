package com.syed.osama.hassan.springbatchintro.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job firstJob() {
        return jobBuilderFactory.get("First Job")
                .start(getFirstStep())
                .next(getSecondStep())
                .build();

    }

    private Step getFirstStep() {
        return stepBuilderFactory.get("First Step")
                .tasklet(getFirstTask())
                .build();
    }

    private Step getSecondStep() {
        return stepBuilderFactory.get("Second Step")
                .tasklet(getSecondTask())
                .build();
    }

    private Tasklet getFirstTask() {
        return (stepContribution, chunkContext) -> {
            System.out.println("This is our first tasklet step.");
            return RepeatStatus.FINISHED;
        };
    }

    private Tasklet getSecondTask() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
                System.out.println("This is our second tasklet step.");
                return RepeatStatus.FINISHED;
            }
        };
    }
}
