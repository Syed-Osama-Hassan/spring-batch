package com.syed.osama.hassan.databasemigration.config;

import com.syed.osama.hassan.databasemigration.entity.mysql.StudentMysql;
import com.syed.osama.hassan.databasemigration.entity.postgres.StudentPostgres;
import com.syed.osama.hassan.databasemigration.listener.JobListener;
import com.syed.osama.hassan.databasemigration.processor.Processor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class JobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("universityDataSource")
    private DataSource dataSource;

    @Autowired
    private Processor processor;

    @Autowired
    private JpaCursorItemReader studentJpaCursorItemReader;

    @Autowired
    private JpaItemWriter studentJpaItemWriter;

    @Autowired
    private JpaTransactionManager jpaTransactionManager;

    @Autowired
    private JobListener jobListener;


    @Bean
    public Job firstJobWithChunkOrientedStep() {
        return jobBuilderFactory.get("Job with chunk oriented step")
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep())
                .listener(jobListener)
                .build();
    }

    private Step firstChunkStep() {
        return stepBuilderFactory.get("First chunk step")
                .transactionManager(jpaTransactionManager)
                .< StudentPostgres, StudentMysql >chunk(10000)
                .reader(studentJpaCursorItemReader)
                .processor(processor)
                .writer(studentJpaItemWriter)
                .build();
    }


}
