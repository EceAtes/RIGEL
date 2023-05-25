package com.example.rigel_v1.domain;

import java.io.File;
import java.util.*;

import com.example.rigel_v1.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Document("Administrations")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Instructor extends FeedbackUser{

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "instructor_id")
    private List<StudentCourse> courses = new LinkedList<>();

    private File eSignature = new File("pom.xml");

    //private Statistics statistics;

    public Instructor(String name, String email, String password, boolean notificationToMail, Department department) {
        super(name, email, password, notificationToMail, Role.INSTRUCTOR, department, new HashMap<>());
        //department.addInstructor(this);
        eSignature = new File("pom.xml");
    }

    public Instructor(List<StudentCourse> courses, File eSignature) { //, Statistics statistics) {
        super();
        this.courses = courses;
        this.eSignature = eSignature;
        //this.statistics = statistics;
    }

    public Instructor(String name, String email, String password, boolean notificationToMail, Department department, Map<Long, Student> students, List<StudentCourse> graded, List<StudentCourse> toBeGraded, File eSignature) {//, Statistics statistics) {
        super(name, email, password, notificationToMail, Role.INSTRUCTOR, department, students);
        this.courses = new LinkedList<>();
        this.eSignature = eSignature;
        //this.statistics = statistics;
        department.addInstructor(this);
    }

    public Instructor(String name, String email, String password, boolean notificationToMail, Department department, Map<Long, Student> students, List<StudentCourse> courses) { //Statistics statistics
        super(name, email, password, notificationToMail, Role.INSTRUCTOR, department, students);
        this.eSignature = eSignature;
        //this.statistics = statistics;
        this.courses = courses;
    }

    public void addCourse(StudentCourse course){
        this.courses.add(course);
        course.setInstructor(this);
    }

    public void removeCourse(StudentCourse course){
        this.courses.remove(course);
    }

}