package com.example.incident.controller;

import com.example.incident.model.Incident;
import com.example.incident.model.Priority;
import com.example.incident.model.Status;
import com.example.incident.service.IncidentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * Web Controller for JSP pages
 * Handles web UI interactions
 */
@Controller
public class WebController {
    
    private static final Logger logger = LoggerFactory.getLogger(WebController.class);
    
    private final IncidentService incidentService;

    @Autowired
    public WebController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    /**
     * Home page - redirect to incidents list
     */
    @GetMapping("/")
    public String home() {
        logger.info("Home page accessed, redirecting to incidents list");
        return "redirect:/web/incidents";
    }

    /**
     * Display all incidents
     */
    @GetMapping("/web/incidents")
    public String listIncidents(Model model) {
        logger.info("Web Request: Display incidents list");
        
        List<Incident> incidents = incidentService.getAllIncidents();
        IncidentService.IncidentStats stats = incidentService.getIncidentStats();
        
        model.addAttribute("incidents", incidents);
        model.addAttribute("stats", stats);
        
        logger.info("Web Response: Displaying {} incidents", incidents.size());
        return "incidents/list";
    }

    /**
     * Show create incident form
     */
    @GetMapping("/web/incidents/new")
    public String showCreateForm(Model model) {
        logger.info("Web Request: Show create incident form");
        
        model.addAttribute("incident", new Incident());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("priorities", Priority.values());
        
        return "incidents/create";
    }

    /**
     * Handle create incident form submission
     */
    @PostMapping("/web/incidents")
    public String createIncident(@Valid @ModelAttribute Incident incident, 
                               BindingResult result, 
                               Model model,
                               RedirectAttributes redirectAttributes) {
        logger.info("Web Request: Create incident - {}", incident.getTitle());
        
        if (result.hasErrors()) {
            logger.warn("Validation errors in create incident form");
            model.addAttribute("statuses", Status.values());
            model.addAttribute("priorities", Priority.values());
            return "incidents/create";
        }
        
        try {
            Incident createdIncident = incidentService.createIncident(incident);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Incident created successfully with ID: " + createdIncident.getId());
            
            logger.info("Web Response: Created incident with ID: {}", createdIncident.getId());
            return "redirect:/web/incidents";
            
        } catch (Exception e) {
            logger.error("Error creating incident: {}", e.getMessage());
            model.addAttribute("errorMessage", "Error creating incident: " + e.getMessage());
            model.addAttribute("statuses", Status.values());
            model.addAttribute("priorities", Priority.values());
            return "incidents/create";
        }
    }

    /**
     * Show incident details
     */
    @GetMapping("/web/incidents/{id}")
    public String showIncident(@PathVariable Long id, Model model) {
        logger.info("Web Request: Show incident details for ID: {}", id);
        
        try {
            Incident incident = incidentService.getIncidentById(id);
            model.addAttribute("incident", incident);
            
            logger.info("Web Response: Displaying incident: {}", incident.getTitle());
            return "incidents/details";
            
        } catch (Exception e) {
            logger.error("Error retrieving incident {}: {}", id, e.getMessage());
            model.addAttribute("errorMessage", "Incident not found");
            return "redirect:/web/incidents";
        }
    }

    /**
     * Update incident status via web form
     */
    @PostMapping("/web/incidents/{id}/status")
    public String updateStatus(@PathVariable Long id, 
                             @RequestParam Status status,
                             RedirectAttributes redirectAttributes) {
        logger.info("Web Request: Update status for incident ID: {} to {}", id, status);
        
        try {
            incidentService.updateIncidentStatus(id, status);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Incident status updated successfully");
            
            logger.info("Web Response: Updated incident status");
            return "redirect:/web/incidents/" + id;
            
        } catch (Exception e) {
            logger.error("Error updating incident status: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Error updating status: " + e.getMessage());
            return "redirect:/web/incidents/" + id;
        }
    }
}