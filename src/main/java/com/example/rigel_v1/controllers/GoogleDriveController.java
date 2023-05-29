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


    /**
     * Upload an internship report, only pdf files are allowed
     *
     * @param folderId
     * @param file
     * @param courseId
     * @param description
     * @throws IOException when an error occurs in the API request
     *
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadInternshipReport(
            @RequestParam("folderId") String folderId,
            @RequestPart("file") MultipartFile file,
            @RequestParam("courseId") Long courseId,
            @RequestParam("description") String description
    ) {
        // Check if a file is uploaded
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded");
        }
        System.out.println("test1");

        // Check file type
        if (!file.getContentType().equalsIgnoreCase("application/pdf")) {
            return ResponseEntity.badRequest().body("Only PDF files are allowed");
        }
        System.out.println("test2");

        try {
            System.out.println("test3");
            String fileKey = googleDriveService.uploadInternshipReport(file, folderId, courseId, description);
            return ResponseEntity.ok("File uploaded successfully. File Key: " + fileKey);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }

    /**
     * Create a semester, set semester dates and create parent folders
     *
     * @throws IOException when an error occurs in the API request
     *
     */
    @PostMapping("/create-semester")
    public FolderCreationResponse createSemester(@RequestBody SemesterRequest request) throws IOException {
        String folderName = request.getFolderName();
        Long userId = request.getUserId();
        String firstDay = request.getFirstDay();
        String lastDay = request.getLastDay();
        String addDropDeadline = request.getAddDropDeadline();
        String withdrawDeadline = request.getWithdrawDeadline();
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            if (user instanceof Admin) {
               if(allDepartmentsHasSecretary()){
                    if(!((Admin)user).isSemesterCreated()){
                        // set semester dates
                        ((Admin)user).setSemesterFirstDay(firstDay);
                        ((Admin)user).setSemesterLastDay(lastDay);
                        ((Admin)user).setAddDropDeadline(addDropDeadline);
                        ((Admin)user).setWithdrawDeadline(withdrawDeadline);
                        ((Admin)user).setSemesterCreated(true);
                        // create folders
                        String parentFolderKey = googleDriveService.createSemesterFolders(folderName, userId);

                        //set department folder keys of secretaries
                        Iterable<Department> departments = departmentRepository.findAll();
                        for (Department department : departments) {
                            ((Admin)user).informSecretary(department.getSecretary());
                        }
                        userRepository.save(user);

                        System.out.println("Semester folder created successfully.");
                        return new FolderCreationResponse(parentFolderKey, false, true, false, false);
                    }
                    else{ // semester shouldn't be created before
                        return new FolderCreationResponse(((Admin)user).getSemesterFolderKey(), false, false, false, true);
                    }
                }
                else{// all departments must have at least one secretary registered to the system
                    return new FolderCreationResponse("-", false, false, true, false);
                }
            }
            else{// unauthorized access
                return new FolderCreationResponse("-", true, false, false, false);
            }
        }
        else{// unauthorized access
            return new FolderCreationResponse("-", true, false, false, false);
        }
    }

    @PostMapping("/start-courses")
    public StudentFolderCreationResponse createStudentFolder(@RequestBody Long userId) {
        try {
            Optional<Users> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                Users user = userOptional.get();
                if (user instanceof Secretary) {
                    if(!(((Secretary)user).isSemesterStarted())){
                        if(((Secretary)user).addDropPeriodPassed()){
                            if(atLeastOneInstructorExist(((Secretary) user).getDepartment().getName())){
                                if(atLeastOneStudentExist(((Secretary) user).getDepartment().getName())){
                                    ((Secretary) user).automatch(usersService);
                                    googleDriveService.createStudentCourseFolders(((Secretary)user).getReportFolderKeys().get(0), true);
                                    googleDriveService.createStudentCourseFolders(((Secretary)user).getReportFolderKeys().get(1), false);
                                    System.out.println("Student folders created successfully.");
                                    ((Secretary) user).setSemesterStarted(true);
                                    return new StudentFolderCreationResponse( false, true, true, true, true, false);
                                }
                                else{
                                    return new StudentFolderCreationResponse(false, false, true, true, false, false);
                                }
                            }
                            else{
                                return new StudentFolderCreationResponse(false, false, true, false, true, false);
                            }
                        }
                        else{//add drop has not passed
                            return new StudentFolderCreationResponse( false, false, false, true, true, false);
                        }
                    }
                    else{ // semester already started
                        return new StudentFolderCreationResponse(false, false, false, true, true, true);
                    }
                }
                else{ // access denied
                    return new StudentFolderCreationResponse( true, false, false, true, true, false);
                }
            }
            else{ // access denied
                return new StudentFolderCreationResponse( true, false, false, true, true, false);
            }
        }catch (IOException e) {
            return new StudentFolderCreationResponse( false, false, false, false, false, false);
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
        Iterable<Instructor> instructors = instructorRepository.findAll();
        for (Instructor instructor : instructors) {
            if (instructor.getDepartment().getName().equals(department)) {
                return true;
            }
        }
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

    @JsonProperty("semesterStarted")
    private boolean semesterStarted;

    public StudentFolderCreationResponse( boolean accessDenied, boolean isCreated, boolean addDropPeriodFinished, boolean atLeastOneInstructorExist, boolean atLeastOneStudentExist, boolean semesterStarted){
        this.accessDenied = accessDenied;
        this.isCreated = isCreated;
        this.addDropPeriodFinished = addDropPeriodFinished;
        this.atLeastOneInstructorExist = atLeastOneInstructorExist;
        this.atLeastOneStudentExist = atLeastOneStudentExist;
        this.semesterStarted = semesterStarted;
    }
}

@Getter
@Setter
@NoArgsConstructor
class SemesterRequest {
    private String folderName;
    private Long userId;
    private String firstDay;
    private String lastDay;
    private String addDropDeadline;
    private String withdrawDeadline;

    SemesterRequest(String folderName, Long userId, String firstDay, String lastDay, String addDropDeadline, String withdrawDeadline){
        this.folderName = folderName;
        this.userId = userId;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
        this.addDropDeadline = addDropDeadline;
        this.withdrawDeadline = withdrawDeadline;
    }
}