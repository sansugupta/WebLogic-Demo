package com.example.incident.service;

import com.example.incident.exception.IncidentNotFoundException;
import com.example.incident.model.Incident;
import com.example.incident.model.Status;
import com.example.incident.repository.IncidentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service layer for Incident management
 * Contains business logic and validation
 */
@Service
public class IncidentService {
    
    private static final Logger logger = LoggerFactory.getLogger(IncidentService.class);
    
    private final IncidentRepository incidentRepository;

    @Autowired
    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    /**
     * Create a new incident
     */
    public Incident createIncident(Incident incident) {
        logger.info("Creating new incident: {}", incident.getTitle());
        
        // Set default status if not provided
        if (incident.getStatus() == null) {
            incident.setStatus(Status.OPEN);
        }
        
        // Set timestamps
        incident.setCreatedAt(LocalDateTime.now());
        incident.setUpdatedAt(LocalDateTime.now());
        
        Incident savedIncident = incidentRepository.save(incident);
        logger.info("Successfully created incident with ID: {}", savedIncident.getId());
        
        return savedIncident;
    }

    /**
     * Get all incidents
     */
    public List<Incident> getAllIncidents() {
        logger.debug("Retrieving all incidents");
        List<Incident> incidents = incidentRepository.findAll();
        logger.debug("Found {} incidents", incidents.size());
        return incidents;
    }

    /**
     * Get incident by ID
     */
    public Incident getIncidentById(Long id) {
        logger.debug("Retrieving incident by ID: {}", id);
        return incidentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Incident not found with ID: {}", id);
                    return new IncidentNotFoundException("Incident not found with ID: " + id);
                });
    }

    /**
     * Update incident status
     */
    public Incident updateIncidentStatus(Long id, Status newStatus) {
        logger.info("Updating status for incident ID: {} to {}", id, newStatus);
        
        Incident incident = getIncidentById(id);
        Status oldStatus = incident.getStatus();
        
        incident.setStatus(newStatus);
        incident.setUpdatedAt(LocalDateTime.now());
        
        Incident updatedIncident = incidentRepository.save(incident);
        logger.info("Successfully updated incident ID: {} status from {} to {}", 
                   id, oldStatus, newStatus);
        
        return updatedIncident;
    }

    /**
     * Update entire incident
     */
    public Incident updateIncident(Long id, Incident updatedIncident) {
        logger.info("Updating incident with ID: {}", id);
        
        Incident existingIncident = getIncidentById(id);
        
        // Update fields
        existingIncident.setTitle(updatedIncident.getTitle());
        existingIncident.setDescription(updatedIncident.getDescription());
        existingIncident.setStatus(updatedIncident.getStatus());
        existingIncident.setPriority(updatedIncident.getPriority());
        existingIncident.setUpdatedAt(LocalDateTime.now());
        
        Incident savedIncident = incidentRepository.save(existingIncident);
        logger.info("Successfully updated incident with ID: {}", id);
        
        return savedIncident;
    }

    /**
     * Delete incident
     */
    public void deleteIncident(Long id) {
        logger.info("Deleting incident with ID: {}", id);
        
        if (!incidentRepository.existsById(id)) {
            logger.warn("Attempted to delete non-existent incident with ID: {}", id);
            throw new IncidentNotFoundException("Incident not found with ID: " + id);
        }
        
        incidentRepository.deleteById(id);
        logger.info("Successfully deleted incident with ID: {}", id);
    }

    /**
     * Get incidents by status
     */
    public List<Incident> getIncidentsByStatus(Status status) {
        logger.debug("Retrieving incidents by status: {}", status);
        List<Incident> incidents = incidentRepository.findByStatus(status);
        logger.debug("Found {} incidents with status: {}", incidents.size(), status);
        return incidents;
    }

    /**
     * Get incident statistics
     */
    public IncidentStats getIncidentStats() {
        logger.debug("Calculating incident statistics");
        
        List<Incident> allIncidents = incidentRepository.findAll();
        
        long totalCount = allIncidents.size();
        long openCount = allIncidents.stream().mapToLong(i -> i.getStatus() == Status.OPEN ? 1 : 0).sum();
        long inProgressCount = allIncidents.stream().mapToLong(i -> i.getStatus() == Status.IN_PROGRESS ? 1 : 0).sum();
        long resolvedCount = allIncidents.stream().mapToLong(i -> i.getStatus() == Status.RESOLVED ? 1 : 0).sum();
        
        return new IncidentStats(totalCount, openCount, inProgressCount, resolvedCount);
    }

    /**
     * Inner class for incident statistics
     */
    public static class IncidentStats {
        private final long totalCount;
        private final long openCount;
        private final long inProgressCount;
        private final long resolvedCount;

        public IncidentStats(long totalCount, long openCount, long inProgressCount, long resolvedCount) {
            this.totalCount = totalCount;
            this.openCount = openCount;
            this.inProgressCount = inProgressCount;
            this.resolvedCount = resolvedCount;
        }

        public long getTotalCount() { return totalCount; }
        public long getOpenCount() { return openCount; }
        public long getInProgressCount() { return inProgressCount; }
        public long getResolvedCount() { return resolvedCount; }
    }
}