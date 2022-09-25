package com.syed.osama.hassan.databasemigration.reader;

import com.syed.osama.hassan.databasemigration.entity.postgres.StudentPostgres;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
public class ItemReader {

    @Autowired
    @Qualifier("postgresEntityManagerFactory")
    private EntityManagerFactory postgresEntityManagerFactory;


    @Bean
    public JpaCursorItemReader<StudentPostgres> studentJpaCursorItemReader() {
        JpaCursorItemReader<StudentPostgres> jpaCursorItemReader = new JpaCursorItemReader<>();
        jpaCursorItemReader.setEntityManagerFactory(postgresEntityManagerFactory);
        jpaCursorItemReader.setQueryString("From StudentPostgres");

        return jpaCursorItemReader;
    }
}
