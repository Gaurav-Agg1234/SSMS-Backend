package com.ssms.service;

import com.ssms.entity.Application;
import com.ssms.entity.ApplicationStatus;
import com.ssms.entity.Scholarship;
import com.ssms.entity.User;
import com.ssms.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private ScholarshipService scholarshipService;
    
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
    
    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }
    
    public List<Application> getApplicationsByStudent(User student) {
        return applicationRepository.findByStudent(student);
    }
    
    public List<Application> getApplicationsByScholarship(Scholarship scholarship) {
        return applicationRepository.findByScholarship(scholarship);
    }
    
    @Transactional
    public Application createApplication(User student, Scholarship scholarship) {
        if (applicationRepository.existsByStudentAndScholarship(student, scholarship)) {
            throw new RuntimeException("Application already exists for this scholarship");
        }
        
        Application application = new Application();
        application.setStudent(student);
        application.setScholarship(scholarship);
        application.setStatus(ApplicationStatus.PENDING);
        
        return applicationRepository.save(application);
    }
    
    @Transactional
    public Application updateApplicationStatus(Long id, ApplicationStatus status) {
        Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Application not found"));
            
        application.setStatus(status);
        
        if (status == ApplicationStatus.APPROVED) {
            scholarshipService.decrementAvailableSeats(application.getScholarship().getId());
        }
        
        return applicationRepository.save(application);
    }
    
    @Transactional
    public Application updateApplication(Long id, Application applicationDetails) {
        Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Application not found"));
            
        application.setDocumentsSubmitted(applicationDetails.getDocumentsSubmitted());
        application.setRemarks(applicationDetails.getRemarks());
        
        return applicationRepository.save(application);
    }
    
    @Transactional
    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }
} 