package com.example.incident.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Health Check Controller
 * Provides application health and status information
 */
@RestController
public class HealthController {
    
    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);
    
    @Value("${app.env:dev}")
    private String environment;
    
    @Value("${spring.application.name:incident-management-system}")
    private String applicationName;

    /**
     * Basic health check endpoint
     * GET /health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        logger.debug("Health check requested");
        
        Map<String, Object> healthInfo = new HashMap<>();
        healthInfo.put("status", "UP");
        healthInfo.put("environment", environment);
        healthInfo.put("application", applicationName);
        healthInfo.put("timestamp", LocalDateTime.now());
        healthInfo.put("version", "1.0.0");
        
        logger.debug("Health check response: {}", healthInfo);
        return ResponseEntity.ok(healthInfo);
    }

    /**
     * Detailed application info
     * GET /info
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        logger.debug("Application info requested");
        
        Map<String, Object> appInfo = new HashMap<>();
        appInfo.put("name", applicationName);
        appInfo.put("description", "Production-style Incident Management System");
        appInfo.put("version", "1.0.0");
        appInfo.put("environment", environment);
        appInfo.put("java.version", System.getProperty("java.version"));
        appInfo.put("build.timestamp", LocalDateTime.now());
        
        return ResponseEntity.ok(appInfo);
    }
}