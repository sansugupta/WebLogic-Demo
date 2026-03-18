package com.example.incident;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Main Spring Boot Application for Incident Management System
 * Extends SpringBootServletInitializer for WAR deployment
 */
@SpringBootApplication
public class IncidentManagementApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(IncidentManagementApplication.class, args);
    }
}