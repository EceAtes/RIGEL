package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.Instructor;
import com.example.rigel_v1.domain.Student;
import com.example.rigel_v1.repositories.InstructorRepository;
import com.example.rigel_v1.repositories.StudentRepository;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/students")
@RestController
@CrossOrigin("http://localhost:3000")
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public void addStudent(@NonNull @RequestBody Student student){
        this.studentRepository.save(student);
    }

    @GetMapping//this function is a get request (fetches sth from the database)
    //works but since all Maps, etc. must be non-null
    public Optional<Student> getAllStudents(){
        Long a = 6L;
        return studentRepository.findById(a);
    }
    /*public Iterable<Instructor> getAllInstructors(){
        return instructorRepository.findAll();
    }*/

    @RequestMapping("/students")
    public String getStudents(Model model){
        model.addAttribute("students", studentRepository.findAll());
        return "students/list";
    }
}
