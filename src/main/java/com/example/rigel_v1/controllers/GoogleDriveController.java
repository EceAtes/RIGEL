package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.*;
import com.example.rigel_v1.repositories.*;
import com.example.rigel_v1.service.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import lombok.*;


@RestController
@CrossOrigin("http://localhost:3000")
public class GoogleDriveController {

    private final GoogleDriveService googleDriveService;
    private final UsersService usersService;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public GoogleDriveController(GoogleDriveService googleDriveService, UserRepository userRepository,
                                 DepartmentRepository departmentRepository, InstructorRepository instructorRepository,
                                 StudentRepository studentRepository, UsersService usersService ) {
        this.googleDriveService = googleDriveService;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.instructorRepository = instructorRepository;
        this.studentRepository = studentRepository;
        this.usersService = usersService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadInternshipReport(
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
            String fileKey = googleDriveService.uploadInternshipReport(file, folderId, userId);
            return ResponseEntity.ok("File uploaded successfully. File Key: " + fileKey);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }

    @PostMapping("/create-semester")
    public FolderCreationResponse createSemester(@RequestParam("folderName") String folderName, @RequestParam("userId") Long userId,
                                                 @RequestParam("firstDay") String firstDay, @RequestParam("lastDay") String lastDay,
                                                 @RequestParam("addDropDeadline") String addDropDeadline, @RequestParam("withdrawDeadline") String withdrawDeadline) throws IOException {
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            if (user instanceof Admin) {
                if(allDepartmentsHasSecretary()){
                    if(!((Admin)user).isSemesterStarted()){
                        // set semester dates
                        ((Admin)user).setSemesterFirstDay(firstDay);
                        ((Admin)user).setSemesterLastDay(lastDay);
                        ((Admin)user).setAddDropDeadline(addDropDeadline);
                        ((Admin)user).setWithdrawDeadline(withdrawDeadline);
                        ((Admin)user).setSemesterStarted(true);
                        // create folders
                        String parentFolderKey = googleDriveService.createSemesterFolders(folderName, userId); 
                        
                        //set department folder keys of secretaries
                        Iterable<Department> departments = departmentRepository.findAll();
                        for (Department department : departments) {
                            ((Admin)user).informSecretary(department.getSecretary());
                        }
                        userRepository.save(user);
                                               
                        System.out.println("Folder created successfully.");
                        return new FolderCreationResponse(parentFolderKey, false, true, false, false); 
                    }
                    else{ // semester shouldn't be created before
                        return new FolderCreationResponse(((Admin)user).getSemesterFolderKey(), false, false, false, true); 
                    }
                }
                else{// all departments must have at least one secretary registered to the system
                    return new FolderCreationResponse("", false, false, true, false); 
                }
            }
            else{// unauthorized access
                return new FolderCreationResponse("1", true, false, false, false);
            }
        }
        else{// unauthorized access
            return new FolderCreationResponse("2", true, false, false, false); 
        }            
    }

    @PostMapping("/start-courses")
    public StudentFolderCreationResponse createStudentFolder(@RequestParam("internshipFolderKey") String internshipFolderKey, @RequestParam("userId") Long userId) {
        try {
            Optional<Users> userOptional = userRepository.findById(userId); 
            if (userOptional.isPresent()) {
                Users user = userOptional.get();
                if (user instanceof Secretary) {
                    if(((Secretary)user).getDepartment().isSemesterStarted()){
                        if(((Secretary)user).addDropPeriodPassed()){
                            if(atLeastOneInstructorExist(((Secretary) user).getDepartment().getName())){
                                if(atLeastOneStudentExist(((Secretary) user).getDepartment().getName())){
                                    ((Secretary) user).automatch(usersService);
                                    String folderKey = googleDriveService.createStudentCourseFolders(internshipFolderKey);
                                    System.out.println("Student folders created successfully.");
                                    return new StudentFolderCreationResponse(folderKey, false, true, true, true, true);                             
                                }
                                else{
                                    return new StudentFolderCreationResponse("", false, false, true, true, false);                             
                                }
                            }
                            else{
                                return new StudentFolderCreationResponse("", false, false, true, false, true);                             
                            }
                        }
                        else{
                            return new StudentFolderCreationResponse("", false, false, false, true, true);                             
                        }
                    }
                    else{
                        return new StudentFolderCreationResponse("", false, false, false, true, true);                             
                    }
                }
                else{
                    return new StudentFolderCreationResponse("4", true, false, false, true, true);                             
                }
            }
            else{
                return new StudentFolderCreationResponse("5", true, false, false, true, true);                             
            }
        }catch (IOException e) {
            return new StudentFolderCreationResponse("6", false, false, false, false, false);                             
        }
    }

    public boolean allDepartmentsHasSecretary(){
        Iterable<Department> departments = departmentRepository.findAll();
        for (Department department : departments) {
            if (department.getSecretary() == null) {
                return false;
            }
        }
        return true;
    }
    
    public boolean atLeastOneInstructorExist(String department){
        System.out.println("girdim");
        Iterable<Instructor> instructors = instructorRepository.findAll();
        for (Instructor instructor : instructors) {
            System.out.println("for");
            if (instructor.getDepartment().getName().equals(department)) {
                return true;
            }
        }
        System.out.println("cikiyorum");
        return false;
    }

    public boolean atLeastOneStudentExist(String department){
        Iterable<Student> students = studentRepository.findAll();
        for (Student student : students) {
            if (student.getDepartment().getName().equals(department)) {
                return true;
            }
        }
        return false;
    }
}

@Getter
@Setter
@NoArgsConstructor
class FolderCreationResponse{
    
    @JsonProperty("folderKey")
    private String folderKey;

    @JsonProperty("accessDenied")
    private boolean accessDenied;

    @JsonProperty("isCreated")
    private boolean isCreated;

    @JsonProperty("missingSecretary")
    private boolean missingSecretary;

    @JsonProperty("semesterConflict")
    private boolean semesterConflict;

    public FolderCreationResponse(String folderKey, boolean accessDenied, boolean isCreated, boolean missingSecretary, boolean semesterConflict){
        this.accessDenied = accessDenied;
        this.isCreated = isCreated;
        this.folderKey = folderKey;
        this.missingSecretary = missingSecretary;
        this.semesterConflict = semesterConflict;
    }
}

@Getter
@Setter
@NoArgsConstructor
class StudentFolderCreationResponse{
    
    @JsonProperty("folderKey")
    private String folderKey;

    @JsonProperty("accessDenied")
    private boolean accessDenied;

    @JsonProperty("isCreated")
    private boolean isCreated;

    @JsonProperty("addDropPeriodFinished")
    private boolean addDropPeriodFinished;

    @JsonProperty("atLeastOneInstructorExist")
    private boolean atLeastOneInstructorExist;

    @JsonProperty("atLeastOneStudentExist")
    private boolean atLeastOneStudentExist;

    public StudentFolderCreationResponse(String folderKey, boolean accessDenied, boolean isCreated, boolean addDropPeriodFinished, boolean atLeastOneInstructorExist, boolean atLeastOneStudentExist ){
        this.accessDenied = accessDenied;
        this.isCreated = isCreated;
        this.folderKey = folderKey;
        this.addDropPeriodFinished = addDropPeriodFinished;
        this.atLeastOneInstructorExist = atLeastOneInstructorExist;
        this.atLeastOneStudentExist = atLeastOneStudentExist;

    }
}