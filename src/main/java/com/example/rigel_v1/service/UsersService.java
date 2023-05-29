package com.example.rigel_v1.service;

import com.example.rigel_v1.controllers.CriteriaReportController;
import com.example.rigel_v1.controllers.GradeFormController;
import com.example.rigel_v1.domain.*;
import com.example.rigel_v1.domain.enums.CourseName;
import com.example.rigel_v1.domain.enums.Role;
import com.example.rigel_v1.repositories.CourseRepository;
import com.example.rigel_v1.repositories.UserRepository;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsersService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CriteriaReportController criteriaReportController;
    private final GradeFormController gradeFormController;

    @Autowired
    public UsersService(UserRepository userRepository, CourseRepository courseRepository, CriteriaReportController criteriaReportController, GradeFormController gradeFormController) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.criteriaReportController = criteriaReportController;
        this.gradeFormController = gradeFormController;
    }

    @Transactional
    public void automatch(Department department){
        List<Instructor> instructors = new ArrayList<>();
        for(int i = 0; i < userRepository.count(); i++){
            Optional<Users> optional = userRepository.findById(Long.valueOf(i+1));
            if(optional.isPresent() && optional.get().getRole() == Role.INSTRUCTOR && optional.get().getDepartment().equals(department)){
                instructors.add((Instructor) optional.get());
            }
        }
        System.out.println(instructors);
        int counter = 0;
        for(int i = 0; i < courseRepository.count(); i++){
            Optional<StudentCourse> optional = courseRepository.findById(Long.valueOf(i+1));
            /*if(optional.isPresent()){
                System.out.println(optional.get().getCourseTaker());
            }*/
            counter = counter % instructors.size();
            if(optional.isPresent() && optional.get().getCourseTaker().getDepartment().equals(department)){
                instructors.get(counter).addCourse(optional.get());
                userRepository.save(instructors.get(counter));
                courseRepository.save(optional.get());
                counter++;
            }
        }
    }


    public Users createUser(String name, String email, String password, boolean notifToMail, Role role, Department department, int studentId, CourseName[] courseTypes){
        System.out.println("ENTERED SERVICE");
        if (role == Role.STUDENT){ //equals()??
            Student student = new Student(name,email,password, notifToMail, department, studentId);
            userRepository.save(student);
            for(int i = 0; i < courseTypes.length; i++){
                StudentCourse studentCourse = new StudentCourse(student, courseTypes[i]);
                courseRepository.save(studentCourse);
                student.getCourses().add(studentCourse);
                userRepository.save(student);
                CriteriaReport cReport = criteriaReportController.createCriteriaReport();
                GradeForm gForm = gradeFormController.createGradeForm();
                studentCourse.setCriteriaReport(cReport);
                courseRepository.save(studentCourse);
                studentCourse.getGradeForms().add(gForm);
                courseRepository.save(studentCourse);
            }
            return student;
        }
        else if (role == Role.INSTRUCTOR) {
            Instructor instructor = new Instructor(name, email, password, notifToMail, department);
            userRepository.save(instructor);
            return instructor;
        }
        else if (role == Role.SECRETARY) {
            Secretary secretary = new Secretary(name,email,password, notifToMail, department);
            userRepository.save(secretary);
            return secretary;
        }
        return new Users();
    }

    public void rematchStudent(Long instructorId, Long courseId) {
        Optional<StudentCourse> optional1 = courseRepository.findById(courseId);
        Optional<Users> optional2 = userRepository.findById(instructorId);

        if(optional1.isPresent() && optional2.isPresent()){
            StudentCourse course  = (StudentCourse) optional1.get();
            Instructor oldInstructor = (Instructor) course.getInstructor();
            Instructor instructor = (Instructor) optional2.get();
            oldInstructor.removeCourse(course);
            instructor.addCourse(course);
            course.setInstructor(instructor);
            courseRepository.save(course);

        }
    }

    /*
   private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users findByUsername(String username){

    }*/

}
