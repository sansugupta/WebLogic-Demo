package com.example.incident.controller;

import com.example.incident.model.Incident;
import com.example.incident.model.Status;
import com.example.incident.service.IncidentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Incident Management
 * Provides RESTful APIs for incident operations
 */
@RestController
@RequestMapping("/incidents")
public class IncidentController {
    
    private static final Logger logger = LoggerFactory.getLogger(IncidentController.class);
    
    private final IncidentService incidentService;

    @Autowired
    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    /**
     * Create a new incident
     * POST /incidents
     */
    @PostMapping
    public ResponseEntity<Incident> createIncident(@Valid @RequestBody Incident incident) {
        logger.info("API Request: Create incident - {}", incident.getTitle());
        
        Incident createdIncident = incidentService.createIncident(incident);
        
        logger.info("API Response: Created incident with ID: {}", createdIncident.getId());
        return new ResponseEntity<>(createdIncident, HttpStatus.CREATED);
    }

    /**
     * Get all incidents
     * GET /incidents
     */
    @GetMapping
    public ResponseEntity<List<Incident>> getAllIncidents(
            @RequestParam(required = false) Status status) {
        logger.info("API Request: Get all incidents" + (status != null ? " with status: " + status : ""));
        
        List<Incident> incidents;
        if (status != null) {
            incidents = incidentService.getIncidentsByStatus(status);
        } else {
            incidents = incidentService.getAllIncidents();
        }
        
        logger.info("API Response: Retrieved {} incidents", incidents.size());
        return ResponseEntity.ok(incidents);
    }

    /**
     * Get incident by ID
     * GET /incidents/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable Long id) {
        logger.info("API Request: Get incident by ID: {}", id);
        
        Incident incident = incidentService.getIncidentById(id);
        
        logger.info("API Response: Retrieved incident: {}", incident.getTitle());
        return ResponseEntity.ok(incident);
    }

    /**
     * Update incident status
     * PUT /incidents/{id}/status
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Incident> updateIncidentStatus(
            @PathVariable Long id, 
            @RequestBody Map<String, String> statusUpdate) {
        logger.info("API Request: Update status for incident ID: {}", id);
        
        String statusStr = statusUpdate.get("status");
        if (statusStr == null) {
            throw new IllegalArgumentException("Status is required");
        }
        
        Status newStatus = Status.valueOf(statusStr.toUpperCase());
        Incident updatedIncident = incidentService.updateIncidentStatus(id, newStatus);
        
        logger.info("API Response: Updated incident status to: {}", newStatus);
        return ResponseEntity.ok(updatedIncident);
    }

    /**
     * Update entire incident
     * PUT /incidents/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Incident> updateIncident(
            @PathVariable Long id, 
            @Valid @RequestBody Incident incident) {
        logger.info("API Request: Update incident ID: {}", id);
        
        Incident updatedIncident = incidentService.updateIncident(id, incident);
        
        logger.info("API Response: Updated incident: {}", updatedIncident.getTitle());
        return ResponseEntity.ok(updatedIncident);
    }

    /**
     * Delete incident
     * DELETE /incidents/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        logger.info("API Request: Delete incident ID: {}", id);
        
        incidentService.deleteIncident(id);
        
        logger.info("API Response: Deleted incident ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get incident statistics
     * GET /incidents/stats
     */
    @GetMapping("/stats")
    public ResponseEntity<IncidentService.IncidentStats> getIncidentStats() {
        logger.info("API Request: Get incident statistics");
        
        IncidentService.IncidentStats stats = incidentService.getIncidentStats();
        
        logger.info("API Response: Retrieved incident statistics");
        return ResponseEntity.ok(stats);
    }
}