package com.ssms.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;
    
    @ManyToOne
    @JoinColumn(name = "scholarship_id", nullable = false)
    private Scholarship scholarship;
    
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    
    @Column(name = "application_date")
    private LocalDateTime applicationDate;
    
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Column(name = "remarks")
    private String remarks;
    
    @Column(name = "documents_submitted")
    private String documentsSubmitted;
    
    @PrePersist
    protected void onCreate() {
        applicationDate = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();
        if (status == null) {
            status = ApplicationStatus.PENDING;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }
} 