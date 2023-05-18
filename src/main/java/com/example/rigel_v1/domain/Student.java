package com.example.rigel_v1.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter @Getter @NoArgsConstructor
public class Student extends Users {

    @OneToMany
    private Set<StudentCourse> courses;

    public Student(String name, String email, String password, boolean notificationToMail, Department department) {
        super(name, email, password, notificationToMail, Role.STUDENT, department);
        this.courses = new HashSet<>();
    }

    public Student(String name, String email, String password, boolean notificationToMail, Role role, Department department, Set<StudentCourse> courses, List<Section> sections) {
        super(name, email, password, notificationToMail, Role.STUDENT, department);
        this.courses = courses;
    }

    public void enrollCourse(StudentCourse course){
        courses.add(course);
        //course.setCourseTaker(this);
    }

    @Override
    public String toString() {
        String output = super.toString() + "/nStudent attributes: {" +
                "courses=" + courses +
                '}';
        return output;
    }

}
