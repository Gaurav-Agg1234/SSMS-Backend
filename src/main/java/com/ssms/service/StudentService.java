package com.ssms.service;

import com.ssms.entity.Student;
import com.ssms.entity.User;
import com.ssms.entity.UserRole;
import com.ssms.repository.StudentRepository;
import com.ssms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Transactional
    public Student registerStudent(Student student) {
        // Validate required fields
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (student.getRollNumber() == null || student.getRollNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Roll number is required");
        }
        if (student.getCourse() == null || student.getCourse().trim().isEmpty()) {
            throw new IllegalArgumentException("Course is required");
        }
        if (student.getBranch() == null || student.getBranch().trim().isEmpty()) {
            throw new IllegalArgumentException("Branch is required");
        }
        if (student.getSemester() == null) {
            throw new IllegalArgumentException("Semester is required");
        }
        if (student.getPhoneNumber() == null || student.getPhoneNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number is required");
        }
        
        // Check for duplicate roll number
        if (studentRepository.findByRollNumber(student.getRollNumber()).isPresent()) {
            throw new IllegalArgumentException("Roll number already exists");
        }
        
        // Create user account for the student
        User user = new User();
        user.setUsername(student.getRollNumber());
        user.setEmail(student.getRollNumber() + "@student.ssms.edu"); // Generate email from roll number
        user.setFullName(student.getName());
        user.setPhoneNumber(student.getPhoneNumber());
        user.setRole(UserRole.STUDENT);
        user.setPassword(passwordEncoder.encode(student.getPhoneNumber())); // Use phone number as initial password
        user = userRepository.save(user);
        
        // Set the user in student entity
        student.setUser(user);
        
        // Save the student
        return studentRepository.save(student);
    }
} 