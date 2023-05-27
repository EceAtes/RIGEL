package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.*;
import com.example.rigel_v1.domain.enums.CourseName;
import com.example.rigel_v1.domain.enums.Status;
import com.example.rigel_v1.repositories.CourseRepository;
import com.example.rigel_v1.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PatchMapping("/{id}")
    public void updateCourse(@PathVariable Long id, @RequestBody CourseResponse response){//can be modified upon request
        Optional<StudentCourse> optional = courseRepository.findById(Long.valueOf(id));
        if(optional.isPresent()){
            StudentCourse course = optional.get();
            if(response.getCourseStatus() != null){
                course.setStatus(response.getCourseStatus());
                courseRepository.save(course);
            }
        }
    }

    @PostMapping
    public void addCourse(@NonNull @RequestBody CourseRequest request){
        System.out.println(request.getName());
        System.out.println(request.getStudent_id());
        Optional<Users> optionalUser  =  userRepository.findById(Long.valueOf(request.getStudent_id()));
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            if (user instanceof Student) {
                Student student = (Student) user;
                //System.out.println("Student: " + student);
                StudentCourse course = new StudentCourse(student, request.getName());
                if(request.getCourseStatus() != null)
                {
                    course.setStatus(request.getCourseStatus());
                }
                courseRepository.save(course);
            }
        }

    }


    @GetMapping("/{id}")
    public CourseResponse getCourse(@PathVariable Long id){
        System.out.println("ENTERED getCourse");
        Optional<StudentCourse> optional = courseRepository.findById(id);
        if(optional.isPresent()){
            StudentCourse course = optional.get();
            Long criteriaReportId = 0L;
            if(course.getCriteriaReport() != null){
                criteriaReportId = course.getCriteriaReport().getId();
            }
            Long lastGradeReportId = 0L;
            List<GradeForm> gradeForms = course.getGradeForms();
            if(gradeForms.size() != 0) {
                lastGradeReportId = gradeForms.get(gradeForms.size()-1).getId();
            }

            return new CourseResponse(course.getId(), course.getCourseName(), course.getCourseTaker().getId(), course.getInternshipReportFolderKey(), course.getIterationCount(), criteriaReportId, lastGradeReportId, course.getStatus());
        }
        return null;
    }

    @GetMapping//this function is a get request (fetches sth from the database)//works but since all Maps, etc. must be non-null
    public Optional<StudentCourse> getAllCourses(){
        Long a = 4L;
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

@Getter @Setter
@NoArgsConstructor
class CourseRequest {
    private CourseName name;
    @JsonProperty("student_id")
    private Long student_id;
    private Status courseStatus;
}

@Getter @Setter
@NoArgsConstructor
class CourseResponse {
    @JsonProperty("id")
    private Long id;
    private CourseName name;
    @JsonProperty("student_id")
    private Long student_id;
    private String internshipReportFolderID;
    private int iteration_count;
    private Long criteria_report_id;
    private Long grade_form_id;
    @JsonProperty("courseStatus")
    private Status courseStatus;

    public CourseResponse(Long id, CourseName name, Long student_id, String internshipReportFolderID, int iteration_count, Long criteria_report_id, Long grade_form_id, Status courseStatus) {
        this.id = id;
        this.name = name;
        this.student_id = student_id;
        this.internshipReportFolderID = internshipReportFolderID;
        this.iteration_count = iteration_count;
        this.criteria_report_id = criteria_report_id;
        this.grade_form_id = grade_form_id;
        this.courseStatus = courseStatus;
    }
}
