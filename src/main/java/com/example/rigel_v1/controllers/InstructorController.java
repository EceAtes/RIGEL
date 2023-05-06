package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.Department;
import com.example.rigel_v1.domain.Instructor;
import com.example.rigel_v1.repositories.InstructorRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/instructors")
@RestController
public class InstructorController {
    private final InstructorRepository instructorRepository;

    public InstructorController(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @PostMapping
    public void addInstructor(@NonNull @RequestBody Instructor instructor){
        this.instructorRepository.save(instructor);
    }

    @GetMapping//this function is a get request (fetches sth from the database)
    //works but since all Maps, etc. must be non-null
    public Optional<Instructor> getAllInstructors(){
        Long a = new Long(6);
        return instructorRepository.findById(a);
    }
    /*public Iterable<Instructor> getAllInstructors(){
        return instructorRepository.findAll();
    }*/

    @RequestMapping("/instructors")
    public String getInstructors(Model model){
        model.addAttribute("instructors", instructorRepository.findAll());
        return "instructors/list";
    }
}
