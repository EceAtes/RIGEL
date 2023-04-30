package com.example.rigel_v1.controllers;

import com.example.rigel_v1.repositories.CourseRepository;
import com.example.rigel_v1.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CourseController {
    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @RequestMapping("/courses")
    public String getCourses(Model model){
        model.addAttribute("courses", courseRepository.findAll());
        return "courses/list";
    }
}
