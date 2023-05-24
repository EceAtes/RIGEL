package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.InternshipReport;
import com.example.rigel_v1.domain.Student;
import com.example.rigel_v1.domain.StudentCourse;
import com.example.rigel_v1.domain.Users;
import com.example.rigel_v1.domain.enums.CourseName;
import com.example.rigel_v1.repositories.CourseRepository;
import com.example.rigel_v1.repositories.InternshipReportRepository;
import com.example.rigel_v1.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/internship_report")
public class InternshipReportController {
    private final InternshipReportRepository internshipReportRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public InternshipReportController(InternshipReportRepository internshipReportRepository, UserRepository userRepository, CourseRepository courseRepository) {
        this.internshipReportRepository = internshipReportRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @PostMapping
    public void addInternshipReport(@NonNull @RequestBody InternReportRequest req){
        Optional<Users> optional = userRepository.findById(Long.valueOf(req.getOwnerStudent_id()));
        Optional<StudentCourse> optional1 = courseRepository.findById(Long.valueOf(req.getStudent_course_id()));
        if (optional.isPresent() && optional1.isPresent()) {
            Users student = optional.get();
            StudentCourse course = optional1.get();
            if(student instanceof Student){
                InternshipReport report = new InternshipReport(false, req.getCourseName(), course, (Student) student, req.getText());
                this.internshipReportRepository.save(report);
            }
        }

    }

    @GetMapping("/{id}")
    public Optional<InternshipReport> getInternshipReport(@PathVariable Long id){
        return internshipReportRepository.findById(id);

    }
}

@Getter
@Setter
@NoArgsConstructor
class InternReportRequest{
    @JsonProperty("student_course_id")
    private int student_course_id;
    @JsonProperty("ownerStudent_id")
    private int ownerStudent_id;
    private String text;
    private CourseName courseName;
}