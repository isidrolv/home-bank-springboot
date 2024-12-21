package com.homebank.api.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.dialect.Dialect;

import javax.sql.DataSource;

@Configuration
public class JdbcConfiguration {
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String jdbcUsername;
    @Value("${spring.datasource.password}")
    private String jdbcPassword;
    @Value("${spring.datasource.driver-class-name}")
    private String jdbcDriverClass;

    @Bean(name = "dataSource")
    public DataSource hikariDataSource() {
        return createHikariDataSource();
    }

    private HikariDataSource createHikariDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(jdbcUrl);
        hikariDataSource.setUsername(jdbcUsername);
        hikariDataSource.setPassword(jdbcPassword);
        hikariDataSource.setDriverClassName(jdbcDriverClass);
        return hikariDataSource;
    }

    @Bean
    public Dialect jdbcDialect() {
        return new FirebirdDialect();
    }


}