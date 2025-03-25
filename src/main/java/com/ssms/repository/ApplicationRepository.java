package com.ssms.repository;

import com.ssms.entity.Application;
import com.ssms.entity.ApplicationStatus;
import com.ssms.entity.Scholarship;
import com.ssms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudent(User student);
    List<Application> findByScholarship(Scholarship scholarship);
    List<Application> findByStatus(ApplicationStatus status);
    List<Application> findByStudentAndStatus(User student, ApplicationStatus status);
    boolean existsByStudentAndScholarship(User student, Scholarship scholarship);
} 