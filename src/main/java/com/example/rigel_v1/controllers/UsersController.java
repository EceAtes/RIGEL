package com.example.rigel_v1.controllers;


import com.example.rigel_v1.domain.*;
import com.example.rigel_v1.domain.enums.CourseName;
import com.example.rigel_v1.domain.enums.Role;
import com.example.rigel_v1.domain.enums.Status;
import com.example.rigel_v1.repositories.CourseRepository;
import com.example.rigel_v1.repositories.DepartmentRepository;
import com.example.rigel_v1.repositories.UserRepository;
import com.example.rigel_v1.service.UsersService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/users")
@RestController
@CrossOrigin("http://localhost:3000")
public class UsersController {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;
    private final UsersService usersService;
    private final CriteriaReportController criteriaReportController;
    private final GradeFormController gradeFormController;

    public UsersController(UserRepository userRepository, DepartmentRepository departmentRepository, CourseRepository courseRepository, UsersService usersService, CriteriaReportController criteriaReportController, GradeFormController gradeFormController) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.courseRepository = courseRepository;
        this.usersService = usersService;
        this.criteriaReportController = criteriaReportController;
        this.gradeFormController = gradeFormController;
    }

    //adds a course for a specified instructor //mostly for manue testing
    @RequestMapping("/add_course")
    public void addCourseToInstructor(@NonNull @RequestBody addCourseRequest req) {
        System.out.println(req.getCourseID());
        System.out.println(req.getInstructorID());
        Optional<Users> optional = userRepository.findById(Long.valueOf(req.getInstructorID()));
        Optional<StudentCourse> optional1 = courseRepository.findById(Long.valueOf(req.getCourseID()));
        if (optional.isPresent() && optional1.isPresent()) {
            Instructor instructor = (Instructor) optional.get();
            StudentCourse course = (StudentCourse) optional1.get();
            System.out.println(instructor.getCourses());
            instructor.addCourse(course);
            System.out.println(instructor.getCourses());
            userRepository.save(instructor);
            System.out.println(instructor);
        }
    }

    //this function checks credetials of the users for login. It returns the information about the the user (includes course information for students and instructors)
    @RequestMapping("/login")
    public LoginResponse findUser(@NonNull @RequestBody LoginRequest req) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (int i = 1; i < userRepository.count() + 1; i++) {
            Optional<Users> optional = userRepository.findById(Long.valueOf(i));
            if (optional.isPresent()) {
                Users user = optional.get();
                if (req.getPassword().equals(user.getPassword()) && req.getEmail().equals(user.getEmail())) {
                    if (user.getRole() == Role.INSTRUCTOR) {
                        List<CourseResponseObject> response = new ArrayList<>();
                        Instructor instructor = (Instructor) optional.get();
                        for(int j = 0; j < instructor.getCourses().size(); j++){
                            StudentCourse currCourse = instructor.getCourses().get(j);
                            //CourseResponseObject obj = new CourseResponseObject(currCourse.getStatus(), currCourse.getCourseName(), currCourse.get_TACheck(), currCourse.getCourseTaker().getName());
                            CourseResponseObject obj;
                            //returns the course's last internship report's ID if it exists, else returns it as 0.
                            if(currCourse.getInternshipReports().size() != 0){
                                obj = new CourseResponseObject(currCourse.getId(), currCourse.getStatus(), currCourse.getCourseName(), true, currCourse.getCourseTaker().getName(), currCourse.getInternshipReportFolderKey(), currCourse.getCriteriaReport().getId(), currCourse.getGradeForms().get(currCourse.getGradeForms().size()-1).getId(), currCourse.getInternshipReports().get(currCourse.getInternshipReports().size()-1).getId(), currCourse.getInternshipReportUploadDeadline().format(formatter));

                            } else{
                                obj = new CourseResponseObject(currCourse.getId(), currCourse.getStatus(), currCourse.getCourseName(), true, currCourse.getCourseTaker().getName(), currCourse.getInternshipReportFolderKey(), currCourse.getCriteriaReport().getId(), currCourse.getGradeForms().get(currCourse.getGradeForms().size()-1).getId(), 0L, currCourse.getInternshipReportUploadDeadline().format(formatter));
                            }
                            response.add(obj);
                        }
                        return new LoginResponseAdditionalInfo(true, user.getRole(), user.getName(), user.getEmail(), user.isNotificationToMail(), user.getDepartment().getId(), user.getId(), response);
                    }
                    else if (user.getRole() == Role.STUDENT) {
                        List<CourseResponseObject> response = new ArrayList<>();
                        Student student = (Student) optional.get();
                        for(int j = 0; j < student.getCourses().size(); j++){
                            StudentCourse currCourse = student.getCourses().get(j);
                            //CourseResponseObject obj = new CourseResponseObject(currCourse.getStatus(), currCourse.getCourseName(), currCourse.get_TACheck(), currCourse.getCourseTaker().getName());
                            CourseResponseObject obj;
                            //returns the course's last internship report's ID if it exists, else returns it as 0.
                            if(currCourse.getInternshipReports().size() != 0){
                                obj = new CourseResponseObject(currCourse.getId(), currCourse.getStatus(), currCourse.getCourseName(), true, currCourse.getCourseTaker().getName(), currCourse.getInternshipReportFolderKey(), currCourse.getCriteriaReport().getId(), currCourse.getGradeForms().get(currCourse.getGradeForms().size()-1).getId(), currCourse.getInternshipReports().get(currCourse.getInternshipReports().size()-1).getId(), currCourse.getInternshipReportUploadDeadline().format(formatter));

                            } else{
                                obj = new CourseResponseObject(currCourse.getId(), currCourse.getStatus(), currCourse.getCourseName(), true, currCourse.getCourseTaker().getName(), currCourse.getInternshipReportFolderKey(), currCourse.getCriteriaReport().getId(), currCourse.getGradeForms().get(currCourse.getGradeForms().size()-1).getId(), 0L,  currCourse.getInternshipReportUploadDeadline().format(formatter));
                            }
                            response.add(obj);
                        }
                        return new LoginResponseAdditionalInfo(true, user.getRole(), student.getName(), user.getEmail(), user.isNotificationToMail(), user.getDepartment().getId(), user.getId(), response);
                    }
                    else if(user.getRole() == Role.ADMIN){
                        return new LoginResponse(true, user.getRole(), user.getName(), user.getEmail(), user.isNotificationToMail(), 0L, user.getId());
                    }
                    else {
                        return new LoginResponse(true, user.getRole(), user.getName(), user.getEmail(), user.isNotificationToMail(), user.getDepartment().getId(), user.getId());
                    }
                }
            }
        }
        return new LoginResponse(false, Role.NOT_REGISTERED);
    }

    //adds a new user from JSON file, return information about the user (includes course information for students and instructors)
    @PostMapping
    public void addUser(@NonNull @RequestBody UserRequest request) {
        Optional<Department> optional = departmentRepository.findById(Long.valueOf(request.getDepartment_id()));
        if (optional.isPresent()) {
            Department department = optional.get();
            if (department instanceof Department) {
                if (request.getRole() == Role.STUDENT) { //student
                    Student student = new Student(request.getName(), request.getEmail(), request.getPassword(), request.isNotifToMail(), department, request.getStudentId());
                    this.userRepository.save(student);
                    for (int i = 0; i < request.getCourses().length; i++) {
                        StudentCourse studentCourse = new StudentCourse(student, request.getCourses()[i]);
                        student.getCourses().add(studentCourse);
                        courseRepository.save(studentCourse);
                        //creates criteriaForm and GradeForm of the each course the student takes (they don't include any information at this stage)
                        CriteriaReport cReport = criteriaReportController.createCriteriaReport();
                        GradeForm gForm = gradeFormController.createGradeForm();
                        studentCourse.setCriteriaReport(cReport);
                        courseRepository.save(studentCourse);
                        studentCourse.getGradeForms().add(gForm);
                        courseRepository.save(studentCourse);

                    }
                    this.userRepository.save(student);
                    if (request.getCourses().length == 1) {
                        CourseName dep299 = CourseName.valueOf(department.getName() + "299");
                        if (request.getCourses()[0] == dep299) {

                            department.addStudent(student, 299);
                            this.departmentRepository.save(department);
                        } else {
                            department.addStudent(student, 399);
                            this.departmentRepository.save(department);
                        }
                    } else if (request.getCourses().length == 2) {
                        department.addStudent(student, 299);
                        department.addStudent(student, 399);
                        this.departmentRepository.save(department);
                    }
                    this.userRepository.save(student);
                    System.out.println(student);
                    System.out.println("DEPARTMENT CHECK");
                    System.out.println(department.getStudents_399());

                } else if (request.getRole() == Role.INSTRUCTOR) { //instructor
                    Instructor instructor = new Instructor(request.getName(), request.getEmail(), request.getPassword(), request.isNotifToMail(), department);
                    this.userRepository.save(instructor);
                    System.out.println(instructor);
                    department.addInstructor(instructor);
                } else if (request.getRole() == Role.SECRETARY) { //secretary
                    Secretary secretary = new Secretary(request.getName(), request.getEmail(), request.getPassword(), request.isNotifToMail(), department);
                    this.userRepository.save(secretary);
                    System.out.println(secretary);
                    department.setSecretary(secretary);
                    System.out.println(department.getSecretary());
                    this.departmentRepository.save(department);
                    //System.out.println(secretary);
                } else if (request.getRole() == Role.ADMIN) {//admin_ gotta send an existing departments_id
                    Admin admin = new Admin(request.getName(), request.getEmail(), request.getPassword(), request.isNotifToMail(), null);
                    this.userRepository.save(admin);
                    System.out.println(admin);
                } else {
                    Users user = new Users(request.getName(), request.getEmail(), request.getPassword(), request.isNotifToMail(), Role.NOT_REGISTERED, department);
                    this.userRepository.save(user);
                    //System.out.println(user);
                }
                //this.departmentRepository.save(department);????
            }
        }
    }

    /*//changes the given users department with the given department
    @PatchMapping("/department/{id}")
    public ResponseEntity<Users> updateUserDepartment(@PathVariable Long id, @RequestBody Map<String, Object> patchRequestBody) {
        Optional<Users> optional = userRepository.findById(id);
        if (optional == null) {
            return ResponseEntity.notFound().build();
        }
        Users user = optional.get();
        Integer departmentIdInt = (Integer) patchRequestBody.get("department_id");
        Long departmentId = departmentIdInt.longValue();
        Optional<Department> optional0 = departmentRepository.findById(departmentId);
        if (optional0 == null) {
            return ResponseEntity.notFound().build();
        }
        Department department = optional0.get();

        user.setDepartment(department);
        Users updatedUser = userRepository.save(user);

        return ResponseEntity.ok(updatedUser);
    }*/

    //updates the user information
    //this doesn't change the department
    @PatchMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        Optional<Users> optional = userRepository.findById(id);
        if (optional == null) {
            return ResponseEntity.notFound().build();
        }
        Users user = optional.get();

        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getPassword() != null) {
            user.setPassword(request.getPassword());
        }
        user.setNotificationToMail(request.isNotifToMail());//if JSON doesn't have any "notifToMail" part, this is set to false

        // Save the updated user object
        Users updatedUser = userRepository.save(user);

        return ResponseEntity.ok(updatedUser);
    }

    //for instructors and students
    //returns user information and course information
    @GetMapping("/get/{id}")
    public GetUserRequest getInstructorOrStudent(@PathVariable Long id) {
        Optional<Users> optional = userRepository.findById(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (optional.isPresent()) {
            Users user = optional.get();
            Instructor instructor;
            Student student;
            /*List<Long> courses = new ArrayList<>();*/
            /*List<Status> courses = new ArrayList<>();*/
            List<CourseResponseObject> response = new ArrayList<>();
            if (user instanceof Instructor) {
                instructor = (Instructor) optional.get();
                for(int i = 0; i < instructor.getCourses().size(); i++){
                    StudentCourse currCourse = instructor.getCourses().get(i);
                    //CourseResponseObject obj = new CourseResponseObject(currCourse.getStatus(), currCourse.getCourseName(), currCourse.get_TACheck(), currCourse.getCourseTaker().getName());
                    CourseResponseObject obj;
                    //returns the course's last internship report's ID if it exists, else returns it as 0.
                    if(currCourse.getInternshipReports().size() != 0){
                        obj = new CourseResponseObject(currCourse.getId(), currCourse.getStatus(), currCourse.getCourseName(), true, currCourse.getCourseTaker().getName(), currCourse.getInternshipReportFolderKey(), currCourse.getCriteriaReport().getId(), currCourse.getGradeForms().get(currCourse.getGradeForms().size()-1).getId(), currCourse.getInternshipReports().get(currCourse.getInternshipReports().size()-1).getId(), currCourse.getInternshipReportUploadDeadline().format(formatter));

                    } else{
                        obj = new CourseResponseObject(currCourse.getId(), currCourse.getStatus(), currCourse.getCourseName(), true, currCourse.getCourseTaker().getName(), currCourse.getInternshipReportFolderKey(), currCourse.getCriteriaReport().getId(), currCourse.getGradeForms().get(currCourse.getGradeForms().size()-1).getId(), 0L, currCourse.getInternshipReportUploadDeadline().format(formatter));
                    }
                    response.add(obj);
                }
                return new GetUserRequest(response, instructor);
            }
            else if(user instanceof Student){
                student = (Student) optional.get();
                for(int i = 0; i < student.getCourses().size(); i++){
                    StudentCourse currCourse = student.getCourses().get(i);
                    //CourseResponseObject obj = new CourseResponseObject(currCourse.getStatus(), currCourse.getCourseName(), currCourse.get_TACheck(), currCourse.getCourseTaker().getName());
                    CourseResponseObject obj;
                    //returns the course's last internship report's ID if it exists, else returns it as 0.
                    if(currCourse.getInternshipReports().size() != 0){
                        obj = new CourseResponseObject(currCourse.getId(), currCourse.getStatus(), currCourse.getCourseName(), true, currCourse.getCourseTaker().getName(), currCourse.getInternshipReportFolderKey(), currCourse.getCriteriaReport().getId(), currCourse.getGradeForms().get(currCourse.getGradeForms().size()-1).getId(), currCourse.getInternshipReports().get(currCourse.getInternshipReports().size()-1).getId(), currCourse.getInternshipReportUploadDeadline().format(formatter));

                    } else{
                        obj = new CourseResponseObject(currCourse.getId(), currCourse.getStatus(), currCourse.getCourseName(), true, currCourse.getCourseTaker().getName(), currCourse.getInternshipReportFolderKey(), currCourse.getCriteriaReport().getId(), currCourse.getGradeForms().get(currCourse.getGradeForms().size()-1).getId(), 0L, currCourse.getInternshipReportUploadDeadline().format(formatter));
                    }
                    response.add(obj);
                }
                return new GetUserRequest(response, student);
            }

        }
        return null;
    }

    /*@GetMapping("/student/{id}")
    public GetStudentRequest getStudent(@PathVariable Long id) {
        Optional<Users> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            Student student = (Student) optional.get();
            List<Long> courses = new ArrayList<>();
            for(int i = 0; i < student.getCourses().size(); i++){
                courses.add(student.getCourses().get(i).getId());
            }
            return new GetStudentRequest(courses, student);
        }
        return null;
    }*/

    //for all users
    //returns user information
    @GetMapping("/{id}")
    public Optional<Users> getUser(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    @GetMapping//this function is a get request (fetches sth from the database)
    //works but since all Maps, etc. must be non-null
    public Iterable<Users> getAllUsers() {
        Iterable<Users> list = userRepository.findAll();
        System.out.println(list);
        return list;
    }

    //returns all users' information
    @RequestMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("students", userRepository.findAll());
        return "users/list";
    }

}

//object to be send into findUser() (login function)
@Getter
@Setter
@NoArgsConstructor
class LoginRequest {
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
}

//object that's returned from findUser() (login function) for secretary and admin users
@Getter
@Setter
@NoArgsConstructor
class LoginResponse {
    @JsonProperty("isVerified")
    private boolean isVerified;
    @JsonProperty("role")
    private Role role;
    private String name;
    private String email;
    private boolean notifToMail;
    @JsonProperty("department_id")
    private Long department_id;
    private Long userId;

    public LoginResponse(boolean isVerified, Role role, String name, String email, boolean notifToMail, Long department_id, Long userId) {
        this.isVerified = isVerified;
        this.role = role;
        this.email = email;
        this.name = name;
        this.notifToMail = notifToMail;
        this.department_id = department_id;
        this.userId = userId;
    }

    public LoginResponse(boolean isVerified, Role role) {
        this.isVerified = isVerified;
        this.role = role;
    }
}

//object that's returned from findUser() (login function) for instructor and student users
@Getter
@Setter
@NoArgsConstructor
class LoginResponseAdditionalInfo extends LoginResponse {
    @JsonProperty("course_info")
    private List<CourseResponseObject> courses;


    public LoginResponseAdditionalInfo(boolean isVerified, Role role, String name, String email, boolean notifToMail, Long department_id, Long userId, List<CourseResponseObject> courses) {
        super(isVerified, role, name, email, notifToMail, department_id, userId);
        this.courses = courses;
    }
}


//object to be send to addCourseToInstructor() (fucnction that adds course to a specified instructor)
@Getter
@Setter
@NoArgsConstructor
class addCourseRequest {
    @JsonProperty("instructorID")
    private Long instructorID;
    @JsonProperty("courseID")
    private Long courseID;
}

//object to be send to addUser() (fucntion that creates a new user [for students also creates the courses]) and updateUser (function that changes user data)
@Getter
@Setter
@NoArgsConstructor
class UserRequest {
    private String name;
    private String email;
    private String password;
    @JsonProperty("notifToMail")
    private boolean notifToMail;
    @JsonProperty("role")
    private Role role;
    private int studentId; //for students
    @JsonProperty("department_id")
    private Long department_id;
    private CourseName[] courses; //for students

}

//this is for the special GET mapping funtion getInstructorOrStudent() that returns the general information and additional course information for student
// and instructor users
class GetUserRequest {
    @JsonProperty("course_info")
    private List<CourseResponseObject> courseStatus;
    @JsonProperty("user")
    private Users user;

    /*public GetUserRequest(List<Long> courses, Users user) {
        this.courses = courses;
        this.user = user;
    }*/
    public GetUserRequest(List<CourseResponseObject> courseStatus, Users user) {
        this.courseStatus = courseStatus;
        this.user = user;
    }

}

//this object represents the additional course information to be send when a student or an instructor logs in or a GET request to /get/{id} is made
class CourseResponseObject{
    @JsonProperty("course_id")
    private Long id;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("course_name")
    private CourseName name;
    @JsonProperty("TA_check")
    private boolean check;
    @JsonProperty("student_name")
    private String studentName;
    @JsonProperty("folder_id")
    private String folder_id;
    @JsonProperty("criteriaReportID")
    private Long criteriaReportID;
    @JsonProperty("gradeFormID")
    private Long gradeFormID;
    @JsonProperty("lastInternshipReportID")
    private Long lastInternshipReportID;
    @JsonProperty("deadline")
    private String deadline;

    public CourseResponseObject(Long id, Status status, CourseName name, boolean check, String studentName, String folder_id, Long criteriaReportID, Long gradeFormID, Long lastInternshipReportID, String deadline) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.check = check;
        this.studentName = studentName;
        this.folder_id = folder_id;
        this.criteriaReportID = criteriaReportID;
        this.gradeFormID = gradeFormID;
        this.lastInternshipReportID = lastInternshipReportID;
        this.deadline = deadline;
    }
}