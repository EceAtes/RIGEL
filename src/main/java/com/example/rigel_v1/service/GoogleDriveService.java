package com.example.rigel_v1.service;

import com.example.rigel_v1.domain.*;
import com.example.rigel_v1.repositories.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleDriveService {
        private final Drive googleDrive;
        private final CourseRepository courseRepository;
        private final ReportRepository reportRepository;
        private final UserRepository userRepository;

        @Autowired
        public GoogleDriveService(CourseRepository courseRepository, ReportRepository reportRepository,
                        UserRepository userRepository)
                        throws IOException {
                this.courseRepository = courseRepository;
                this.reportRepository = reportRepository;
                this.userRepository = userRepository;
                // Initialize Google Drive API client
                InputStream credentials = getClass().getResourceAsStream("/credentials.json");
                GoogleCredential googleCredential = GoogleCredential.fromStream(credentials)
                                .createScoped(Collections.singleton(DriveScopes.DRIVE));
                this.googleDrive = new Drive.Builder(googleCredential.getTransport(), googleCredential.getJsonFactory(),
                                googleCredential)
                                .setApplicationName("RIGEL")
                                .build();
        }

        /**
         * Upload a file to Google Drive and store the file link in the user's database.
         *
         * @param file      The file to upload
         * @param folderId  The ID of the folder to upload the file into
         * @param studentId The ID of the user performing the action
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
                StudentCourse studentCourse = courseRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"));
                String reportLink = ("https://drive.google.com/uc?id=" + fileId);

                // Create an InternshipReport instance
                InternshipReport internshipReport = new InternshipReport(studentCourse, studentCourse.getCourseTaker(), reportLink, "description here");
                reportRepository.save(internshipReport);
                // Add the internship report to the student course
                studentCourse.uploadInternshipReport(internshipReport);

                // Save the updated student course
                courseRepository.save(studentCourse);

                return fileId;
        }

        /**
         * Create the semester folder and its subfolders according to reports. Add links
         * to a user's database (admin).
         * 
         * @param file
         * @param folderId
         * @param userId
         * @return
         * @throws IOException when an error occurs in the API request
         */
        public String createSemesterFolders(String folderName, Long userId) throws IOException {
                File semesterFolder = new File();
                semesterFolder.setName(folderName);
                semesterFolder.setMimeType("application/vnd.google-apps.folder");

                File createdSemesterFolder = googleDrive.files().create(semesterFolder)
                                .setFields("id")
                                .execute();

                // find admin(?)????????????????????????????????????
                Admin admin = (Admin) userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException( "User not found, cannot create semester folders."));

                System.out.println("Semester Folder ID: " + createdSemesterFolder.getId());
                String semesterFolderLink = "https://drive.google.com/drive/folders/" + createdSemesterFolder.getId();
                System.out.println("Semester ( View ): " + semesterFolderLink);
                admin.setSemesterFolderID(createdSemesterFolder.getId());

                // set permissions to allow anyone with the link to view
                Permission permission = new Permission();
                permission.setType("anyone");
                permission.setRole("reader");
                googleDrive.permissions().create(createdSemesterFolder.getId(), permission)
                                .setFields("id")
                                .execute();

                // create viewable report folders & add links to database
                String[] subfolderNames = { "Internship Reports", "Criteria Reports", "Grade Forms" };

                for (String subfolderName : subfolderNames) {
                        File subfolder = new File();
                        subfolder.setName(subfolderName);
                        subfolder.setMimeType("application/vnd.google-apps.folder");
                        subfolder.setParents(Collections.singletonList(createdSemesterFolder.getId()));

                        File createdSubfolder = googleDrive.files().create(subfolder)
                                        .setFields("id")
                                        .execute();

                        String subfolderLink = "https://drive.google.com/drive/folders/" + createdSubfolder.getId();
                        System.out.println("Report Folder ( View ): " + subfolderLink);
                        admin.saveReportFolderID( createdSubfolder.getId());
                }
                userRepository.save(admin);
                return createdSemesterFolder.getId();
        }

        /**
         */
        public String createStudentCourseFolder(String parentFolderKey, Long userId) throws IOException {
                StudentCourse course = (StudentCourse) courseRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException( "StudentCourse not found, cannot create student folder."));

                File studentCourseFolder = new File();
                studentCourseFolder.setParents(Collections.singletonList(parentFolderKey));
                String folderName = course.getCourseTaker().getDepartment() + "-" + course.getCourseName() + " - " + course.getCourseTaker().getName(); //STUDENT ID + COURSE???
                studentCourseFolder.setName(folderName);
                studentCourseFolder.setMimeType("application/vnd.google-apps.folder");

                
                File createdStudentCourseFolder = googleDrive.files().create(studentCourseFolder).setFields("id").execute();

                // set permissions to allow anyone with the link to view
                Permission permission = new Permission();
                permission.setType("anyone");
                permission.setRole("reader");
                googleDrive.permissions().create(createdStudentCourseFolder.getId(), permission)
                                .setFields("id")
                                .execute();

                System.out.println("Student Course Key: " + createdStudentCourseFolder.getId());
                String studentFolderLink = "https://drive.google.com/drive/folders/" + createdStudentCourseFolder.getId();
                System.out.println("Student Folder ( View ): " + studentFolderLink);
                course.setInternshipReportFolderID(createdStudentCourseFolder.getId());

                courseRepository.save(course);
                return createdStudentCourseFolder.getId();
        }
}