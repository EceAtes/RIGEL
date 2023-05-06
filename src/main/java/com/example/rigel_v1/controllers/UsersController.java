package com.example.rigel_v1.controllers;


import com.example.rigel_v1.domain.*;
import com.example.rigel_v1.domain.enums.CourseName;
import com.example.rigel_v1.repositories.DepartmentRepository;
import com.example.rigel_v1.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/users")
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
                if(request.getRole() == 2){
                    Student student = new Student(request.getName(),request.getEmail(), request.getPassword(), request.isNotifToMail(), department);
                    this.userRepository.save(student);
                }
                else if(request.getRole() == 4){
                    Instructor instructor = new Instructor(request.getName(),request.getEmail(), request.getPassword(), request.isNotifToMail(), department);
                    this.userRepository.save(instructor);
                } else{
                    Users user = new Users(request.getName(),request.getEmail(), request.getPassword(), request.isNotifToMail(), Users.Role.NOT_REGISTERED, department);
                    this.userRepository.save(user);
                }
            }
        }



    }

    @GetMapping//this function is a get request (fetches sth from the database)
    //works but since all Maps, etc. must be non-null
    public Optional<Users> getAllUsers(){
        Long a = 6L;
        return userRepository.findById(a);
    }
    /*public Iterable<Users> getAllUsers(){
        return userRepository.findAll();
    }*/

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
