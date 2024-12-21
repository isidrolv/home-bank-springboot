package com.homebank.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories(basePackages = "com.homebank.api.dao")
public class HomeBankRestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(HomeBankRestApiApplication.class, args);
    }

}
