package com.example.rigel_v1.controllers;


import com.example.rigel_v1.domain.*;
import com.example.rigel_v1.domain.enums.CourseName;
import com.example.rigel_v1.domain.enums.Role;
import com.example.rigel_v1.repositories.CourseRepository;
import com.example.rigel_v1.repositories.DepartmentRepository;
import com.example.rigel_v1.repositories.UserRepository;
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

import java.util.Map;
import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UsersController {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;

    public UsersController(UserRepository userRepository, DepartmentRepository departmentRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.courseRepository = courseRepository;
    }

    @RequestMapping("/login")
    public LoginResponse findUser(@NonNull @RequestBody LoginRequest req){
        for(int i = 0; i < userRepository.count(); i++){
            Optional<Users> optional = userRepository.findById(Long.valueOf(i));
            if (optional.isPresent() ) {
                Users user = optional.get();
                if(req.getPassword().equals(user.getPassword())  && req.getEmail().equals(user.getEmail())){
                    return new LoginResponse( true, user.getRole(), user.getName(),user.getEmail(), user.isNotificationToMail(), user.getDepartment().getId(), user.getId() );
                }
            }
        }
        return new LoginResponse( false, Role.NOT_REGISTERED);
    }

    @PostMapping
    public void addUser(@NonNull @RequestBody UserRequest request){
        Optional<Department> optional = departmentRepository.findById(Long.valueOf(request.getDepartment_id()));
        if (optional.isPresent()) {
            Department department = optional.get();
            if (department instanceof Department) {
                if(request.getRole() == Role.STUDENT){ //student
                    Student student = new Student(request.getName(),request.getEmail(), request.getPassword(), request.isNotifToMail(), department, request.getStudentId());
                    this.userRepository.save(student);
                    for(int i = 0; i < request.getCourses().length; i++){
                        StudentCourse studentCourse = new StudentCourse(student, request.getCourses()[i]);
                        student.getCourses().add(studentCourse);
                        courseRepository.save(studentCourse);
                    }
                    this.userRepository.save(student);
                    if(request.getCourses().length == 1){
                        CourseName dep299 = CourseName.valueOf(department.getName() + "299");
                        if(request.getCourses()[0] == dep299){

                            department.addStudent(student, 299);
                            this.departmentRepository.save(department);
                        } else {
                            department.addStudent(student, 399);
                            this.departmentRepository.save(department);
                        }
                    } else if(request.getCourses().length == 2) {
                        department.addStudent(student, 299);
                        department.addStudent(student, 399);
                        this.departmentRepository.save(department);
                    }
                    this.userRepository.save(student);
                    System.out.println(student);
                    System.out.println("DEPARTMENT CHECK");
                    System.out.println(department.getStudents_399());

                }
                else if(request.getRole() == Role.INSTRUCTOR){ //instructor
                    Instructor instructor = new Instructor(request.getName(),request.getEmail(), request.getPassword(), request.isNotifToMail(), department);
                    this.userRepository.save(instructor);
                    System.out.println(instructor);
                    department.addInstructor(instructor);
                }
                else if(request.getRole() == Role.SECRETARY){ //secretary
                    Secretary secretary = new Secretary(request.getName(),request.getEmail(), request.getPassword(), request.isNotifToMail(), department);
                    this.userRepository.save(secretary);
                    System.out.println(secretary);
                    department.setSecretary(secretary);
                    System.out.println(department.getSecretary());
                    this.departmentRepository.save(department);
                    //System.out.println(secretary);
                }
                else if(request.getRole() == Role.ADMIN){//admin_ gotta send an existing departments_id
                    Admin admin = new Admin(request.getName(), request.getEmail(), request.getPassword(), request.isNotifToMail(), null);
                    this.userRepository.save(admin);
                    System.out.println(admin);

                }
                else{
                    Users user = new Users(request.getName(),request.getEmail(), request.getPassword(), request.isNotifToMail(), Role.NOT_REGISTERED, department);
                    this.userRepository.save(user);
                    //System.out.println(user);
                }
            }
        }
    }

    @PatchMapping("/department/{id}")
    public ResponseEntity<Users> updateUserDepartment(@PathVariable Long id, @RequestBody Map<String, Object> patchRequestBody){
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
    }


    //doesn't change department
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

    @GetMapping("/{id}")
    public Optional<Users> getUser(@PathVariable Long id){
        return userRepository.findById(id);
    }

    @GetMapping//this function is a get request (fetches sth from the database)
    //works but since all Maps, etc. must be non-null
    public Iterable<Users> getAllUsers(){
        Iterable<Users> list = userRepository.findAll();
        System.out.println(list);
        return list;
    }

    @RequestMapping("/users")
    public String getUsers(Model model){
        model.addAttribute("students", userRepository.findAll());
        return "users/list";
    }

}

@Getter
@Setter
@NoArgsConstructor
class LoginRequest{
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
}

@Getter @Setter
@NoArgsConstructor
class LoginResponse{
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

    public LoginResponse(boolean isVerified, Role role, String name, String email,boolean notifToMail, Long department_id, Long userId) {
        this.isVerified = isVerified;
        this.role = role;
        this.name = name;
        this.notifToMail = notifToMail;
        this.department_id = department_id;
        this.userId = userId;
    }

    public LoginResponse(boolean isVerified, Role role){
        this.isVerified = isVerified;
        this.role = role;
    }
}

@Getter @Setter
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
