package com.example.rigel_v1.controllers;

import com.example.rigel_v1.service.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class GoogleDriveController {

    private final GoogleDriveService googleDriveService;

    @Autowired
    public GoogleDriveController(GoogleDriveService googleDriveService) {
        this.googleDriveService = googleDriveService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("folderId") String folderId,
            @RequestPart("file") MultipartFile file,
            @RequestParam("userId") Long userId // shoud be changed

    ) {
        // Check if a file is uploaded
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded");
        }

        // Check file type
        if (!file.getContentType().equalsIgnoreCase("application/pdf")) {
            return ResponseEntity.badRequest().body("Only PDF files are allowed");
        }

        try {
            String fileId = googleDriveService.uploadFile(file, folderId, userId);
            return ResponseEntity.ok("File uploaded successfully. File ID: " + fileId);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }

    @PostMapping("/create-semester")
    public ResponseEntity<String> createPublicFolder(@RequestParam("folderName") String folderName,
            @RequestParam("userId") Long userId) {
        try {
            googleDriveService.createSemesterFolders(folderName, userId);
            return ResponseEntity.ok("Folder created successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create folder: " + e.getMessage());
        }
    }

}
