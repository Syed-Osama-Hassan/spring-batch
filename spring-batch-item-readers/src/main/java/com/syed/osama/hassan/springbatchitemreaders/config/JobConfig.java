package com.syed.osama.hassan.springbatchitemreaders.config;

import com.syed.osama.hassan.springbatchitemreaders.processor.FirstItemProcessor;
import com.syed.osama.hassan.springbatchitemreaders.reader.FirstItemReader;
import com.syed.osama.hassan.springbatchitemreaders.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
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
    private FirstItemReader firstItemReader;

    @Autowired
    private FirstItemProcessor firstItemProcessor;

    @Autowired
    private FirstItemWriter firstItemWriter;

    @Bean
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
