package com.example.rigel_v1.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student extends Users {
    //@OneToMany(fetch = FetchType.EAGER)
    @OneToMany
    @JoinColumn(name = "student_id")
    private Set<InternshipReport> oldReports = new HashSet<>();

    private int studentId = -1;

    //@OneToMany(fetch = FetchType.EAGER)
    @OneToMany
    private Set<StudentCourse> courses = new HashSet<>();

    public Student(String name, String email, String password, boolean notificationToMail, Department department, int studentId) {
        super(name, email, password, notificationToMail, Role.STUDENT, department);
        this.oldReports = new HashSet<>();
        this.courses = new HashSet<>();
        this.studentId = studentId;
    }

    public Student(String name, String email, String password, boolean notificationToMail, Role role, Department department,Set<InternshipReport> oldReports, Set<StudentCourse> courses, List<Section> sections) {
        super(name, email, password, notificationToMail, Role.STUDENT, department );
        this.oldReports = oldReports;
        this.courses = courses;
    }
    public void enrollCourse(StudentCourse course){
        courses.add(course);
        //course.setCourseTaker(this);
    }

    @Override
    public String toString() {
        String output = super.toString() + "/nStudent attributes: {" +
                "oldReports=" + oldReports +
                ", courses=" + courses +
                '}';
        return output;
    }

    public boolean takes(int i) {
        for (StudentCourse course: courses) {
            if(Integer.parseInt((course.getCourseName() + "").substring(2)) == i)
                return true;
        }
        return false;
    }
}
