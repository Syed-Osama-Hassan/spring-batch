package com.syed.osama.hassan.springbatchintro.config;

import com.syed.osama.hassan.springbatchintro.listener.FirstJobListener;
import com.syed.osama.hassan.springbatchintro.listener.FirstStepListener;
import com.syed.osama.hassan.springbatchintro.processor.FirstItemProcessor;
import com.syed.osama.hassan.springbatchintro.reader.FirstItemReader;
import com.syed.osama.hassan.springbatchintro.service.SecondTasklet;
import com.syed.osama.hassan.springbatchintro.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
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

    @Autowired
    private SecondTasklet secondTasklet;

    @Autowired
    private FirstJobListener firstJobListener;

    @Autowired
    private FirstStepListener firstStepListener;

    @Autowired
    private FirstItemReader firstItemReader;

    @Autowired
    private FirstItemProcessor firstItemProcessor;

    @Autowired
    private FirstItemWriter firstItemWriter;

    @Bean("First Job")
    public Job firstJob() {
        return jobBuilderFactory.get("First Job")
                .incrementer(new RunIdIncrementer())
                .start(getFirstStep())
                .next(getSecondStep())
                .listener(firstJobListener)
                .build();

    }


    private Step getFirstStep() {
        return stepBuilderFactory.get("First Step")
                .tasklet(getFirstTask())
                .listener(firstStepListener)
                .build();
    }

    private Step getSecondStep() {
        return stepBuilderFactory.get("Second Step")
                .tasklet(secondTasklet)
                .build();
    }

    private Tasklet getFirstTask() {
        return (stepContribution, chunkContext) -> {
            System.out.println("This is our first tasklet step.");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean("Second Job")
    public Job firstJobWithChunkOrientedStep() {
        return jobBuilderFactory.get("Job with chunk oriented step")
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep())
                .build();

    }

    private Step firstChunkStep() {
        return stepBuilderFactory.get("First chunk step")
                .<Integer, Long>chunk(3)
                .reader(firstItemReader)
                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }

}
