package com.homebank.api;

import com.homebank.api.campaign.OpenAiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories(basePackages = "com.homebank.api.dao")
@EnableConfigurationProperties(OpenAiProperties.class)
public class HomeBankRestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(HomeBankRestApiApplication.class, args);
    }

}
