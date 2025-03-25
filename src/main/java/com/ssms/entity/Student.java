package com.ssms.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String rollNumber;
    
    @Column(nullable = false)
    private String course;
    
    @Column(nullable = false)
    private String branch;
    
    @Column(nullable = false)
    private Integer semester;
    
    private String section;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    // Document paths
    private String aadharCardPath;
    private String photoPath;
    private String signaturePath;
    private String collegeIdPath;
    private String tenthMarksheetPath;
    private String twelfthMarksheetPath;
    private String semesterMarksheetsPath;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 