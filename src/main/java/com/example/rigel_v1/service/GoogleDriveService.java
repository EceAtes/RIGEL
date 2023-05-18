package com.example.rigel_v1.service;

import com.example.rigel_v1.domain.*;
import com.example.rigel_v1.repositories.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

@Service
public class GoogleDriveService {
    private final Drive googleDrive;
    private final CourseRepository courseRepository;
    private final ReportRepository reportRepository;

    @Autowired    
    public GoogleDriveService(CourseRepository courseRepository, ReportRepository reportRepository) throws IOException {
        this.courseRepository = courseRepository;
        this.reportRepository = reportRepository;
        // Initialize Google Drive API client
        InputStream credentials = getClass().getResourceAsStream("/credentials.json");
        GoogleCredential googleCredential = GoogleCredential.fromStream(credentials)
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        this.googleDrive = new Drive.Builder(googleCredential.getTransport(), googleCredential.getJsonFactory(), googleCredential)
                .setApplicationName("RIGEL")
                .build();
    }

   /**
 * Upload a file to Google Drive and store the file link in the user's database.
 *
 * @param file      The file to upload
 * @param folderId  The ID of the folder to upload the file into
 * @param studentId    The ID of the user performing the action
 * @return The ID of the uploaded file
 * @throws IOException when an error occurs in the API request
 */
public String uploadFile(MultipartFile file, String folderId, Long userId) throws IOException {
    File driveFile = new File();
    driveFile.setName(file.getOriginalFilename());
    driveFile.setParents(Collections.singletonList(folderId));

    byte[] fileBytes = file.getBytes();
    ByteArrayContent mediaContent = new ByteArrayContent(file.getContentType(), fileBytes);

    File uploadedFile = googleDrive.files().create(driveFile, mediaContent)
            .setFields("id")
            .execute();

    String fileId = uploadedFile.getId();

    // Update the user's database entry with the file link
    StudentCourse studentCourse = courseRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    String reportLink = ("https://drive.google.com/uc?id=" + fileId);

     // Create an InternshipReport instance
     InternshipReport internshipReport = new InternshipReport(studentCourse, studentCourse.getCourseTaker(), reportLink);
    reportRepository.save(internshipReport);
     // Add the internship report to the student course
     studentCourse.uploadInternshipReport(internshipReport);
     
     // Save the updated student course
     courseRepository.save(studentCourse);

     return fileId;
}
}


/**
     * Get a list of all files in a folder.
     *
     * @param folderId The ID of the folder
     * @return The list of files in the folder
     * @throws IOException when an error occurs in the API request
     *
    public FileList getFilesInFolder(String folderId) throws IOException {
        return googleDrive.files().list()
                .setQ("'" + folderId + "' in parents")
                .setFields("files(id, name)")
                .execute();
    }*/