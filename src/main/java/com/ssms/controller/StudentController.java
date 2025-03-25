package com.ssms.controller;

import com.ssms.entity.Student;
import com.ssms.service.FileUploadService;
import com.ssms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private FileUploadService fileUploadService;
    
    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(
            @RequestPart("student") Student student,
            @RequestPart("documents") Map<String, MultipartFile> documents) {
        
        // Upload documents
        if (documents.containsKey("aadharCard")) {
            student.setAadharCardPath(fileUploadService.uploadFile(documents.get("aadharCard"), "aadharCard"));
        }
        if (documents.containsKey("photo")) {
            student.setPhotoPath(fileUploadService.uploadFile(documents.get("photo"), "photo"));
        }
        if (documents.containsKey("signature")) {
            student.setSignaturePath(fileUploadService.uploadFile(documents.get("signature"), "signature"));
        }
        if (documents.containsKey("collegeId")) {
            student.setCollegeIdPath(fileUploadService.uploadFile(documents.get("collegeId"), "collegeId"));
        }
        if (documents.containsKey("tenthMarksheet")) {
            student.setTenthMarksheetPath(fileUploadService.uploadFile(documents.get("tenthMarksheet"), "tenthMarksheet"));
        }
        if (documents.containsKey("twelfthMarksheet")) {
            student.setTwelfthMarksheetPath(fileUploadService.uploadFile(documents.get("twelfthMarksheet"), "twelfthMarksheet"));
        }
        if (documents.containsKey("semesterMarksheets")) {
            student.setSemesterMarksheetsPath(fileUploadService.uploadFile(documents.get("semesterMarksheets"), "semesterMarksheets"));
        }
        
        Student savedStudent = studentService.registerStudent(student);
        return ResponseEntity.ok(savedStudent);
    }
    
    @PostMapping("/register/basic")
    public ResponseEntity<?> registerStudentBasic(@RequestBody Student student) {
        Student savedStudent = studentService.registerStudent(student);
        return ResponseEntity.ok(savedStudent);
    }
} 