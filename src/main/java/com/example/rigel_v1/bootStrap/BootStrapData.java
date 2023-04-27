package com.example.rigel_v1.bootStrap;
import com.example.rigel_v1.domain.*;
import com.example.rigel_v1.repositories.*;

import java.io.File;
import java.util.Set;

import org.aspectj.apache.bcel.generic.Instruction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    public BootStrapData(DepartmentRepository departmentRepository, UserRepository userRepository, CourseRepository courseRepository, InstructorRepository instructorRepository) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
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

        Instructor instructor1 = new Instructor("Eray Tuzun", "tuzun@gmail.com", "2345", false, CS );
        Instructor instructor2 = new Instructor("Bahar Yetis", "yetis@gmail.com", "ieieie", false, CS);
        instructorRepository.save(instructor1);
        instructorRepository.save(instructor2);

        Course cs299 = new Course(Course.CourseCode._299, instructor1);
        Course ie299 = new Course(Course.CourseCode._399);
        ie299.setInstructor(instructor2);
        Course cs399 = new Course(Course.CourseCode._399, instructor1);
        courseRepository.save(cs299);
        courseRepository.save(ie299);
        courseRepository.save(cs399);

        A.enrollCourse(cs399);
        B.enrollCourse(ie299);
        A.enrollCourse(cs299);
        userRepository.save(A);
        userRepository.save(B);
        courseRepository.save(cs299);
        courseRepository.save(ie299);
        courseRepository.save(cs399);

        System.out.println("No of users: " + userRepository.count());

    }
}
