package com.example.rigel_v1.controllers;

import com.example.rigel_v1.repositories.DepartmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @RequestMapping("/departments")
    public String getDepartments(Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        return "departments/list";
    }
}
