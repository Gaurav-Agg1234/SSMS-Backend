package com.ssms.controller;

import com.ssms.entity.Application;
import com.ssms.entity.ApplicationStatus;
import com.ssms.entity.Scholarship;
import com.ssms.entity.User;
import com.ssms.service.ApplicationService;
import com.ssms.service.ScholarshipService;
import com.ssms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ScholarshipService scholarshipService;

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {
        return applicationService.getApplicationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public List<Application> getApplicationsByStudent(@PathVariable Long studentId) {
        User student = userService.getUserById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return applicationService.getApplicationsByStudent(student);
    }

    @GetMapping("/scholarship/{scholarshipId}")
    public List<Application> getApplicationsByScholarship(@PathVariable Long scholarshipId) {
        Scholarship scholarship = scholarshipService.getScholarshipById(scholarshipId)
                .orElseThrow(() -> new RuntimeException("Scholarship not found"));
        return applicationService.getApplicationsByScholarship(scholarship);
    }

    @PostMapping
    public ResponseEntity<Application> createApplication(
            @RequestParam Long studentId,
            @RequestParam Long scholarshipId) {
        try {
            User student = userService.getUserById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            Scholarship scholarship = scholarshipService.getScholarshipById(scholarshipId)
                    .orElseThrow(() -> new RuntimeException("Scholarship not found"));
            
            Application application = applicationService.createApplication(student, scholarship);
            return ResponseEntity.ok(application);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {
        try {
            Application updatedApplication = applicationService.updateApplicationStatus(id, status);
            return ResponseEntity.ok(updatedApplication);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(
            @PathVariable Long id,
            @RequestBody Application applicationDetails) {
        try {
            Application updatedApplication = applicationService.updateApplication(id, applicationDetails);
            return ResponseEntity.ok(updatedApplication);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        try {
            applicationService.deleteApplication(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 