package com.ssms.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "scholarships")
public class Scholarship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private BigDecimal amount;
    
    @Column(name = "eligibility_criteria")
    private String eligibilityCriteria;
    
    @Column(name = "application_deadline")
    private LocalDateTime applicationDeadline;
    
    @Column(name = "academic_year")
    private String academicYear;
    
    @Column(name = "total_seats")
    private Integer totalSeats;
    
    @Column(name = "available_seats")
    private Integer availableSeats;
    
    @Enumerated(EnumType.STRING)
    private ScholarshipStatus status;
    
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private User provider;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (availableSeats == null) {
            availableSeats = totalSeats;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 