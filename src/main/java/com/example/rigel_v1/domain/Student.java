package com.example.rigel_v1.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Student extends Users {
    @ElementCollection
    private Set<String> oldReports;
    //private Set<InternshipReport> oldReports;

    @ElementCollection
    private Set<String> courses;
    //private Set<Course> courses;


    public Student() {
    }

    public Student(String name, String email, String password, boolean notificationToMail, Department department) {
        super(name, email, password, notificationToMail, Role.STUDENT, department);
        this.oldReports = new HashSet<>();
        this.courses = new HashSet<>();
    }

    public Student(String name, String email, String password, boolean notificationToMail, Role role, Department department, Set<Notification> notification, Set<String> oldReports, Set<String> courses) {
        super(name, email, password, notificationToMail, role, department, notification);
        this.oldReports = oldReports;
        this.courses = courses;
    }


    public Set<String> getOldReports() {
        return oldReports;
    }

    public void setOldReports(Set<String> oldReports) {
        this.oldReports = oldReports;
    }

    public Set<String> getCourses() {
        return courses;
    }

    public void setCourses(Set<String> courses) {
        this.courses = courses;
    }
}
