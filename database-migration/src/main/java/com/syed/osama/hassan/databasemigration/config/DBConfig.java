package com.syed.osama.hassan.databasemigration.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class DBConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "universityDataSource")
    @ConfigurationProperties(prefix = "spring.universitydatasource")
    public DataSource universityDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "postgresDataSource")
    @ConfigurationProperties(prefix = "spring.postgresdatasource")
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public EntityManagerFactory postgresEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(postgresDataSource());
        em.setPackagesToScan("com.syed.osama.hassan.databasemigration.entity.postgres");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.afterPropertiesSet();

        return em.getObject();
    }

    @Bean
    public EntityManagerFactory mysqlEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(universityDataSource());
        em.setPackagesToScan("com.syed.osama.hassan.databasemigration.entity.mysql");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.afterPropertiesSet();

        return em.getObject();
    }

}
