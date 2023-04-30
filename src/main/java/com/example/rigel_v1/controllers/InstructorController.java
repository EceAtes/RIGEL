package com.example.rigel_v1.controllers;

import com.example.rigel_v1.repositories.InstructorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InstructorController {
    private final InstructorRepository instructorRepository;

    public InstructorController(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @RequestMapping("/instructors")
    public String getInstructors(Model model){
        model.addAttribute("instructors", instructorRepository.findAll());
        return "instructors/list";
    }
}
