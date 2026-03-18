package com.example.incident.exception;

/**
 * Exception thrown when an incident is not found
 */
public class IncidentNotFoundException extends RuntimeException {
    
    public IncidentNotFoundException(String message) {
        super(message);
    }
    
    public IncidentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}