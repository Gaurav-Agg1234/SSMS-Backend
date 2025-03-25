package com.ssms.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadService {
    private final String uploadDir = "uploads/";
    
    public FileUploadService() {
        createUploadDirectory();
    }
    
    private void createUploadDirectory() {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }
    
    public String uploadFile(MultipartFile file, String documentType) {
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                throw new IllegalArgumentException("File name cannot be null");
            }
            
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + fileExtension;
            
            Path filePath = Paths.get(uploadDir, newFilename);
            Files.copy(file.getInputStream(), filePath);
            
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Could not store file " + file.getOriginalFilename(), e);
        }
    }
} 