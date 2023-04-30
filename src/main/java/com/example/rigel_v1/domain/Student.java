package com.example.rigel_v1.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Student extends Users {
    @ElementCollection
    private Set<InternshipReport> oldReports;

    @ElementCollection
    private Set<Course> courses;


    public Student() {
    }

    public Student(String name, String email, String password, boolean notificationToMail, Department department) {
        super(name, email, password, notificationToMail, Role.STUDENT, department);
        this.oldReports = new HashSet<>();
        this.courses = new HashSet<>();
    }

    public Student(String name, String email, String password, boolean notificationToMail, Role role, Department department, Set<Notification> notification, Set<InternshipReport> oldReports, Set<Course> courses) {
        super(name, email, password, notificationToMail, role, department, notification);
        this.oldReports = oldReports;
        this.courses = courses;
    }

    public boolean enrollCourse(Course course){
        //if() course capacity and other constraints satisfied, department match etc.
        courses.add(course);
        return true;
    }

    public Set<InternshipReport> getOldReports() {
        return oldReports;
    }

    public void setOldReports(Set<InternshipReport> oldReports) {
        this.oldReports = oldReports;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        String output = super.toString() + "/nStudent attributes: {" +
                "oldReports=" + oldReports +
                ", courses=" + courses +
                '}';
        return output;
    }
}
