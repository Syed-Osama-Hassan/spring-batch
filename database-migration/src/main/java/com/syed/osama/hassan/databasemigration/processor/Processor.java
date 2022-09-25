package com.syed.osama.hassan.databasemigration.processor;

import com.syed.osama.hassan.databasemigration.entity.mysql.StudentMysql;
import com.syed.osama.hassan.databasemigration.entity.postgres.StudentPostgres;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<StudentPostgres, StudentMysql> {

    @Override
    public StudentMysql process(StudentPostgres studentPostgres) throws Exception {
        System.out.println("PROCESSING | ID: " + studentPostgres.getId());

        StudentMysql bean = new StudentMysql();
        bean.setId(studentPostgres.getId());
        bean.setFirstName(studentPostgres.getFirstName());
        bean.setLastName(studentPostgres.getLastName());
        bean.setEmail(studentPostgres.getEmail());
        bean.setActive(studentPostgres.getIsActive() != null?
                Boolean.valueOf(studentPostgres.getIsActive()) : false);

        return bean;
    }

}
