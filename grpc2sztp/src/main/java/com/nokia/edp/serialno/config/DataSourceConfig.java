package com.nokia.edp.serialno.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Configuration for multiple data sources:
 * 1. FRNG Oracle Database (Source)
 * 2. MAC Address SQL Server (Source)
 * 3. Target SQL Server (Destination)
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.nokia.edp.serialno.repository",
    entityManagerFactoryRef = "targetEntityManagerFactory",
    transactionManagerRef = "targetTransactionManager"
)
public class DataSourceConfig {

    // FRNG Oracle Database Configuration
    @Bean
    @ConfigurationProperties("frng.datasource")
    public DataSourceProperties frngDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource frngDataSource() {
        return frngDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public JdbcTemplate frngJdbcTemplate(@Qualifier("frngDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    // MAC Address SQL Server Configuration
    @Bean
    @ConfigurationProperties("mac.datasource")
    public DataSourceProperties macDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource macDataSource() {
        return macDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public JdbcTemplate macJdbcTemplate(@Qualifier("macDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    // Target SQL Server Configuration (Primary for JPA)
    @Bean
    @Primary
    @ConfigurationProperties("target.datasource")
    public DataSourceProperties targetDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource targetDataSource() {
        return targetDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @Primary
    public JdbcTemplate targetJdbcTemplate(@Qualifier("targetDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean targetEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("targetDataSource") DataSource dataSource) {
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        
        return builder
                .dataSource(dataSource)
                .packages("com.nokia.edp.serialno.entity")
                .persistenceUnit("target")
                .properties(properties)
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager targetTransactionManager(
            @Qualifier("targetEntityManagerFactory") LocalContainerEntityManagerFactoryBean targetEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(targetEntityManagerFactory.getObject()));
    }
}
