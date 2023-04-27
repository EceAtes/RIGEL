package com.example.rigel_v1.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Student extends Users {
    @OneToMany
    @JoinColumn(name = "student_id")
    private Set<InternshipReport> oldReports;
    //private Set<InternshipReport> oldReports;

    @OneToMany
    @JoinColumn(name = "student_id")
    private Set<Course> courses;
    //private Set<Course> courses;


    public Student() {
    }

    public Student(String name, String email, String password, boolean notificationToMail, Department department) {
        super(name, email, password, notificationToMail, Role.STUDENT, department);
        this.oldReports = new HashSet<>();
        this.courses = new HashSet<>();
    }

    public Student(String name, String email, String password, boolean notificationToMail, Role role, Department department, Set<Notification> notification, Set<InternshipReport> oldReports, Set<Course> courses, List<Section> sections) {
        super(name, email, password, notificationToMail, Role.STUDENT, department, notification);
        this.oldReports = oldReports;
        this.courses = courses;
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

    public void enrollCourse(Course course){
        courses.add(course);
        course.setCourseTaker(this);
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
