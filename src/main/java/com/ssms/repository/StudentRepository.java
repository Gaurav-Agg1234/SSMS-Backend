package com.ssms.repository;

import com.ssms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByRollNumber(String rollNumber);
    Optional<Student> findByRollNumber(String rollNumber);
} 