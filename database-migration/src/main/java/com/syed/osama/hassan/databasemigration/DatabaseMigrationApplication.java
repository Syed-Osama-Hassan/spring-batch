package com.syed.osama.hassan.databasemigration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class DatabaseMigrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseMigrationApplication.class, args);
	}

}
