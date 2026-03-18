package com.example.incident.repository;

import com.example.incident.model.Incident;
import com.example.incident.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * In-memory repository for Incident management
 * Thread-safe implementation using ConcurrentHashMap
 */
@Repository
public class IncidentRepository {
    
    private static final Logger logger = LoggerFactory.getLogger(IncidentRepository.class);
    
    private final Map<Long, Incident> incidents = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Save an incident (create or update)
     */
    public Incident save(Incident incident) {
        if (incident.getId() == null) {
            incident.setId(idGenerator.getAndIncrement());
            logger.info("Creating new incident with ID: {}", incident.getId());
        } else {
            logger.info("Updating incident with ID: {}", incident.getId());
        }
        
        incidents.put(incident.getId(), incident);
        return incident;
    }

    /**
     * Find incident by ID
     */
    public Optional<Incident> findById(Long id) {
        logger.debug("Finding incident by ID: {}", id);
        return Optional.ofNullable(incidents.get(id));
    }

    /**
     * Find all incidents
     */
    public List<Incident> findAll() {
        logger.debug("Finding all incidents. Total count: {}", incidents.size());
        return new ArrayList<>(incidents.values());
    }

    /**
     * Find incidents by status
     */
    public List<Incident> findByStatus(Status status) {
        logger.debug("Finding incidents by status: {}", status);
        return incidents.values().stream()
                .filter(incident -> incident.getStatus() == status)
                .collect(Collectors.toList());
    }

    /**
     * Delete incident by ID
     */
    public boolean deleteById(Long id) {
        logger.info("Deleting incident with ID: {}", id);
        return incidents.remove(id) != null;
    }

    /**
     * Check if incident exists
     */
    public boolean existsById(Long id) {
        return incidents.containsKey(id);
    }

    /**
     * Get total count of incidents
     */
    public long count() {
        return incidents.size();
    }
}