package com.example.rigel_v1.controllers;


import com.example.rigel_v1.domain.*;
import com.example.rigel_v1.domain.enums.CourseName;
import com.example.rigel_v1.repositories.DepartmentRepository;
import com.example.rigel_v1.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UsersController {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public UsersController(UserRepository userRepository, DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    @PostMapping
    public void addUser(@NonNull @RequestBody UserRequest request){
        Optional<Department> optional = departmentRepository.findById(Long.valueOf(request.getDepartment_id()));
        if (optional.isPresent()) {
            Department department = optional.get();
            if (department instanceof Department) {
                if(request.getRole() == 2){ //student
                    Student student = new Student(request.getName(),request.getEmail(), request.getPassword(), request.isNotifToMail(), department);
                    /*if(student.takes(299)){
                        department.addStudent(student, 299);
                    }
                    if(student.takes(399)){
                        department.addStudent(student, 399);
                    }*/
                    this.userRepository.save(student);
                    System.out.println(student);
                }
                else if(request.getRole() == 4){ //instructor
                    Instructor instructor = new Instructor(request.getName(),request.getEmail(), request.getPassword(), request.isNotifToMail(), department);
                    this.userRepository.save(instructor);
                    System.out.println(instructor);
                }
                else if(request.getRole() == 6){ //secretary
                    Secretary secretary = new Secretary(request.getName(),request.getEmail(), request.getPassword(), request.isNotifToMail(), department);
                    this.userRepository.save(secretary);
                    //System.out.println(secretary);
                } else{
                    Users user = new Users(request.getName(),request.getEmail(), request.getPassword(), request.isNotifToMail(), Users.Role.NOT_REGISTERED, department);
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
class UserRequest {
    private String name;
    private String email;
    private String password;
    @JsonProperty("notifToMail")
    private boolean notifToMail;
    private int role;

    @JsonProperty("department_id")
    private Long department_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isNotifToMail() {
        return notifToMail;
    }

    public void setNotifToMail(boolean notifToMail) {
        this.notifToMail = notifToMail;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Long getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Long department_id) {
        this.department_id = department_id;
    }
}
