package com.nokia.edp.serialno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main Spring Boot Application for EDP Serial Number Service
 * This service fetches data from FRNG Oracle DB and MAC Address SQL Server,
 * then writes the combined data to the partner SQL Server database.
 */
@SpringBootApplication
@EnableScheduling
public class EdpSerialnoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdpSerialnoServiceApplication.class, args);
    }
}
