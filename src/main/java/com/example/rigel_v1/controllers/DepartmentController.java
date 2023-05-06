package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.Department;
import com.example.rigel_v1.repositories.DepartmentRepository;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/departments")
@RestController
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @PostMapping
    public void addDepartment(@NonNull @RequestBody Department department){
        this.departmentRepository.save(department);
    }

    @GetMapping//this function is a get request (fetches sth from the database)
    //works but since all Maps, etc. must be non-null
    public Optional<Department> getAllDepartments(){
        Long a = new Long(3);
        return departmentRepository.findById(a);
    }
    /*public Iterable<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }*/

    @RequestMapping("/departments")
    public String getDepartments(Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        return "departments/list";
    }
}
