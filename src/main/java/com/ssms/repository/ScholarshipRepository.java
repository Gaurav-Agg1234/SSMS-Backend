package com.ssms.repository;

import com.ssms.entity.Scholarship;
import com.ssms.entity.ScholarshipStatus;
import com.ssms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScholarshipRepository extends JpaRepository<Scholarship, Long> {
    List<Scholarship> findByStatus(ScholarshipStatus status);
    List<Scholarship> findByProvider(User provider);
    List<Scholarship> findByStatusAndAvailableSeatsGreaterThan(ScholarshipStatus status, Integer seats);
} 