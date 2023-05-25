package com.example.rigel_v1.controllers;

import com.example.rigel_v1.service.GoogleDriveService;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import lombok.*;


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
            String fileKey = googleDriveService.uploadFile(file, folderId, userId);
            return ResponseEntity.ok("File uploaded successfully. File Key: " + fileKey);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }

    @PostMapping("/create-semester")
    public FolderCreationResponse createSemester(@RequestParam("folderName") String folderName, @RequestParam("userId") Long userId) {
        try {
            String parentFolderKey = googleDriveService.createSemesterFolders(folderName, userId);
            //olan userların folderları oluşturulacak
            // tarihler setlenecek
            System.out.println("Folder created successfully.");
            return new FolderCreationResponse(true, parentFolderKey);
        } catch (IOException e) {
            return new FolderCreationResponse(false, "none");
        }
    }

    @PostMapping("/student-folder")
    public FolderCreationResponse createStudentFolder(@RequestParam("parentFolderKey") String parentFolderKey, @RequestParam("userId") Long userId) {
        try {
            String folderKey = googleDriveService.createStudentCourseFolder(parentFolderKey, userId);
            System.out.println("Student folder created successfully.");
            return new FolderCreationResponse(true, folderKey);
        } catch (IOException e) {
            return new FolderCreationResponse(false, "none");
        }
    }
}

@Getter
@Setter
@NoArgsConstructor
class FolderCreationResponse{
    @JsonProperty("isCreated")
    private boolean isCreated;

    @JsonProperty("folderKey")
    private String folderKey;

    public FolderCreationResponse(boolean isCreated, String folderKey){
        this.isCreated = isCreated;
        this.folderKey = folderKey;
    }
}

