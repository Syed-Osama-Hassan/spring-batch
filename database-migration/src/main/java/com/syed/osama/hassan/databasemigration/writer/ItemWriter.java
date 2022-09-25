package com.syed.osama.hassan.databasemigration.writer;

import com.syed.osama.hassan.databasemigration.entity.mysql.StudentMysql;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
public class ItemWriter {

    @Autowired
    @Qualifier("mysqlEntityManagerFactory")
    private EntityManagerFactory mysqlEntityManagerFactory;

    @Bean
    public JpaItemWriter<StudentMysql> studentJpaItemWriter() {
        JpaItemWriter<StudentMysql> studentJpaItemWriter = new JpaItemWriter<>();
        studentJpaItemWriter.setEntityManagerFactory(mysqlEntityManagerFactory);

        return studentJpaItemWriter;
    }

}
