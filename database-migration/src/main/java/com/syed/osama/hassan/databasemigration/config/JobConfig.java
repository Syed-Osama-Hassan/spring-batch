//package com.syed.osama.hassan.databasemigration.config;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//
//@Configuration
//public class JobConfig {
//    @Autowired
//    private JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    private StepBuilderFactory stepBuilderFactory;
//
//    @Autowired
//    @Qualifier("universityDataSource")
//    private DataSource dataSource;
//
//    @Autowired
//    @Qualifier("postgresEntityManagerFactory")
//    private EntityManagerFactory postgresEntityManagerFactory;
//
//    @Autowired
//    @Qualifier("mysqlEntityManagerFactory")
//    private EntityManagerFactory mysqlEntityManagerFactory;
//
//    @Bean
//    public Job firstJobWithChunkOrientedStep() {
//        return jobBuilderFactory.get("Job with chunk oriented step")
//                .incrementer(new RunIdIncrementer())
//                .start(firstChunkStep())
//                .build();
//    }
//
//    private Step firstChunkStep() {
//        return stepBuilderFactory.get("First chunk step")
//                .chunk(3)
//                .build();
//    }
//
//
//}
