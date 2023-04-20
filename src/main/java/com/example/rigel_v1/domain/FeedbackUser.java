package com.example.rigel_v1.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FeedbackUser extends Users{


    //private Map<Integer, List<Course>> students;
    private Map<Integer, Student> students;

    public FeedbackUser() {
    }

    public FeedbackUser(Map<Integer, Student> students) {
        this.students = students;
    }

    public FeedbackUser(String name, String email, String password, boolean notificationToMail, Role role, Department department, Map<Integer, Student> students) {
        super(name, email, password, notificationToMail, role, department);
        this.students = students;
    }

    public FeedbackUser(String name, String email, String password, boolean notificationToMail, Role role, Department department, Set<Notification> notification, Map<Integer, Student> students) {
        super(name, email, password, notificationToMail, role, department, notification);
        this.students = students;
    }

    public Map<Integer, Student> getStudents() {
        return students;
    }

    public void setStudents(Map<Integer, Student> students) {
        this.students = students;
    }
}
