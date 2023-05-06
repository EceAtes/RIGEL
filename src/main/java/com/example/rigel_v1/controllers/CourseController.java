package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.Instructor;
import com.example.rigel_v1.domain.Student;
import com.example.rigel_v1.domain.StudentCourse;
import com.example.rigel_v1.domain.Users;
import com.example.rigel_v1.domain.enums.CourseName;
import com.example.rigel_v1.repositories.CourseRepository;
import com.example.rigel_v1.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/courses")
@RestController
public class CourseController {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseController(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public void addCourse(@NonNull @RequestBody CourseRequest request){
        System.out.println(request.getName());
        System.out.println(request.getStudentId());
        Optional<Users> optionalUser  =  userRepository.findById(Long.valueOf(request.getStudentId()));
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            if (user instanceof Student) {
                Student student = (Student) user;
                //System.out.println("Student: " + student);
                StudentCourse course = new StudentCourse();
                course.setCourseName(request.getName());
                course.setCourseTaker(student);
                courseRepository.save(course);
            }
        }

    }

    @GetMapping//this function is a get request (fetches sth from the database)//works but since all Maps, etc. must be non-null
    public Optional<StudentCourse> getAllCourses(){
        Long a = 5L;
        return courseRepository.findById(a);
    }
    /*public Iterable<StudentCourse> getAllCourses(){
        return instructorRepository.findAll();
    }*/

    @RequestMapping("/courses")
    public String getCourses(Model model){
        model.addAttribute("courses", courseRepository.findAll());
        return "courses/list";
    }
}

