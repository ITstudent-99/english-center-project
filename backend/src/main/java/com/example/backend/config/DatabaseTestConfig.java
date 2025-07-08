package com.example.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class DatabaseTestConfig {

    @Bean
    CommandLineRunner testDatabaseConnection(DataSource dataSource) {
        return args -> {
            try (Connection connection = dataSource.getConnection()) {
                System.out.println("DB connected: " +
                        connection.getMetaData().getURL() +
                        ", user: " + connection.getMetaData().getUserName());
            } catch (Exception e) {
                System.out.println("DB connection failed");
                e.printStackTrace();
            }
        };
    }
}
