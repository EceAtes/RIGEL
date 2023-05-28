package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.*;
import com.example.rigel_v1.domain.enums.CourseName;
import com.example.rigel_v1.repositories.CourseRepository;
import com.example.rigel_v1.repositories.FeedbackRepository;
import com.example.rigel_v1.repositories.InternshipReportRepository;
import com.example.rigel_v1.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.internal.build.AllowPrintStacktrace;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/internship_report")
@CrossOrigin("http://localhost:3000")
public class InternshipReportController {
    private final InternshipReportRepository internshipReportRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final FeedbackRepository feedbackRepository;

    public InternshipReportController(InternshipReportRepository internshipReportRepository, UserRepository userRepository, CourseRepository courseRepository, FeedbackRepository feedbackRepository) {
        this.internshipReportRepository = internshipReportRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @PostMapping
    public void addInternshipReport(@NonNull @RequestBody InternReportRequest req){
        Optional<Users> optional = userRepository.findById(Long.valueOf(req.getOwnerStudent_id()));
        Optional<StudentCourse> optional1 = courseRepository.findById(Long.valueOf(req.getStudent_course_id()));
        if (optional.isPresent() && optional1.isPresent()) {
            Users student = optional.get();
            StudentCourse course = optional1.get();
            if(student instanceof Student){ 
                InternshipReport report = new InternshipReport((Student) student, " ", req.getText()); // REPORT LINK_?????????????????????????
                this.internshipReportRepository.save(report);
                this.courseRepository.save(course);
                course.getInternshipReports().add(report);
                this.courseRepository.save(course);
            }
        }

    }

    @GetMapping("/{id}")
    public Optional<InternshipReport> getInternshipReport(@PathVariable Long id){
        return internshipReportRepository.findById(id);

    }

    @PatchMapping("/give_feedback/{courseId}")
    public Feedback addFeedback(@PathVariable Long courseId, @RequestBody  String feedback){
        Optional<StudentCourse> optional = courseRepository.findById(courseId);
        if(optional.isPresent()){
            System.out.println("ENTERED GIVE FEEDBACK");
            StudentCourse course = optional.get();
            Feedback newFeedback = new Feedback(feedback);
            feedbackRepository.save(newFeedback);
            System.out.println(course.getInternshipReports().size()-1);
            course.getInternshipReports().get(course.getInternshipReports().size()-1).getFeedbacks().add(newFeedback);
            internshipReportRepository.save(course.getInternshipReports().get(course.getInternshipReports().size()-1));
            return newFeedback;
        }
        return null;
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