package com.example.rigel_v1.bootStrap;
import com.example.rigel_v1.domain.*;
import com.example.rigel_v1.repositories.*;

import java.util.Set;

import org.aspectj.apache.bcel.generic.Instruction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    CourseRepository courseRepository;
    ReportRepository reportRepository;

    public BootStrapData(DepartmentRepository departmentRepository, UserRepository userRepository, CourseRepository courseRepository, ReportRepository reportRepository) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Department CS = new Department("CS");
        Department IE = new Department("IE");
        departmentRepository.save(CS);
        departmentRepository.save(IE);

        Student A = new Student("A", "a@gmail.com", "1234", true, null);
        CS.addStudent(A, 299);

        userRepository.save(A);
        departmentRepository.save(CS);

        Student B = new Student("B", "b@gmail.com", "1235", false, null);
        IE.addStudent(B, 399);

        userRepository.save(B);
        departmentRepository.save(IE);

        Student C = new Student("C", "c@gmail.com", "1236", true, null);
        IE.addStudent(C, 399);

        userRepository.save(C);
        departmentRepository.save(IE);

        System.out.println("Started");

        System.out.println("No of users: " + userRepository.count());
        System.out.println("No of departments: " + departmentRepository.count());
        System.out.println("No of CS students: " + CS.getTotalStuNo());
        System.out.println("No of IE students: " + IE.getTotalStuNo());

        //Instructor instructor1 = new Instructor("Eray Tuzun", "tuzun@gmail.com", "319319", false, CS, null, null, null, null, null, null);
        //Instructor instructor2 = new Instructor("Bahar Yetis", "yetis@gmail.com", "ieieie", false, CS, null, null, null, null, null, null);


        Course stuA_cs299 = new Course(Course.CourseCode._299, "instructor1");
        Course stuB_ie299 = new Course(Course.CourseCode._399);
        stuB_ie299.setInstructor("instructor2");
        Course stuA_cs399 = new Course(Course.CourseCode._399, "instructor1");

        courseRepository.save(stuA_cs299);
        courseRepository.save(stuB_ie299);
        courseRepository.save(stuA_cs399);

        //enroll section will include enrollCourse
        A.enrollCourse(stuA_cs299);
        B.enrollCourse(stuB_ie299);
        A.enrollCourse(stuA_cs399);

        GradeForm stuA_gradeForm = new GradeForm(true, Report.CourseName.CS299, A, "instructor1", Report.ReportStatus.changable);
        reportRepository.save(stuA_gradeForm);

        stuA_cs299.uploadGradeForm(stuA_gradeForm);
        userRepository.save(A);

        System.out.println("\n\nNo of courses: " + courseRepository.count());
        System.out.println("No of reports: " + reportRepository.count());



    }
}
