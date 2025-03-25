package com.ssms.service;

import com.ssms.entity.Scholarship;
import com.ssms.entity.ScholarshipStatus;
import com.ssms.entity.User;
import com.ssms.repository.ScholarshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ScholarshipService {
    
    @Autowired
    private ScholarshipRepository scholarshipRepository;
    
    public List<Scholarship> getAllScholarships() {
        return scholarshipRepository.findAll();
    }
    
    public Optional<Scholarship> getScholarshipById(Long id) {
        return scholarshipRepository.findById(id);
    }
    
    public List<Scholarship> getActiveScholarships() {
        return scholarshipRepository.findByStatus(ScholarshipStatus.ACTIVE);
    }
    
    public List<Scholarship> getScholarshipsByProvider(User provider) {
        return scholarshipRepository.findByProvider(provider);
    }
    
    @Transactional
    public Scholarship createScholarship(Scholarship scholarship) {
        scholarship.setStatus(ScholarshipStatus.DRAFT);
        return scholarshipRepository.save(scholarship);
    }
    
    @Transactional
    public Scholarship updateScholarship(Long id, Scholarship scholarshipDetails) {
        Scholarship scholarship = scholarshipRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Scholarship not found"));
            
        scholarship.setName(scholarshipDetails.getName());
        scholarship.setDescription(scholarshipDetails.getDescription());
        scholarship.setAmount(scholarshipDetails.getAmount());
        scholarship.setEligibilityCriteria(scholarshipDetails.getEligibilityCriteria());
        scholarship.setApplicationDeadline(scholarshipDetails.getApplicationDeadline());
        scholarship.setAcademicYear(scholarshipDetails.getAcademicYear());
        scholarship.setTotalSeats(scholarshipDetails.getTotalSeats());
        
        return scholarshipRepository.save(scholarship);
    }
    
    @Transactional
    public Scholarship updateScholarshipStatus(Long id, ScholarshipStatus status) {
        Scholarship scholarship = scholarshipRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Scholarship not found"));
            
        scholarship.setStatus(status);
        return scholarshipRepository.save(scholarship);
    }
    
    @Transactional
    public void deleteScholarship(Long id) {
        scholarshipRepository.deleteById(id);
    }
    
    @Transactional
    public void decrementAvailableSeats(Long id) {
        Scholarship scholarship = scholarshipRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Scholarship not found"));
            
        if (scholarship.getAvailableSeats() > 0) {
            scholarship.setAvailableSeats(scholarship.getAvailableSeats() - 1);
            scholarshipRepository.save(scholarship);
        }
    }
} 