package com.ssms.controller;

import com.ssms.entity.Scholarship;
import com.ssms.entity.ScholarshipStatus;
import com.ssms.entity.User;
import com.ssms.service.ScholarshipService;
import com.ssms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scholarships")
@CrossOrigin(origins = "*")
public class ScholarshipController {

    @Autowired
    private ScholarshipService scholarshipService;
    
    @Autowired
    private UserService userService;

    @GetMapping
    public List<Scholarship> getAllScholarships() {
        return scholarshipService.getAllScholarships();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Scholarship> getScholarshipById(@PathVariable Long id) {
        return scholarshipService.getScholarshipById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public List<Scholarship> getActiveScholarships() {
        return scholarshipService.getActiveScholarships();
    }

    @GetMapping("/provider/{providerId}")
    public List<Scholarship> getScholarshipsByProvider(@PathVariable Long providerId) {
        User provider = userService.getUserById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));
        return scholarshipService.getScholarshipsByProvider(provider);
    }

    @PostMapping
    public Scholarship createScholarship(@RequestBody Scholarship scholarship) {
        return scholarshipService.createScholarship(scholarship);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Scholarship> updateScholarship(@PathVariable Long id, @RequestBody Scholarship scholarshipDetails) {
        try {
            Scholarship updatedScholarship = scholarshipService.updateScholarship(id, scholarshipDetails);
            return ResponseEntity.ok(updatedScholarship);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Scholarship> updateScholarshipStatus(
            @PathVariable Long id,
            @RequestParam ScholarshipStatus status) {
        try {
            Scholarship updatedScholarship = scholarshipService.updateScholarshipStatus(id, status);
            return ResponseEntity.ok(updatedScholarship);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScholarship(@PathVariable Long id) {
        try {
            scholarshipService.deleteScholarship(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 