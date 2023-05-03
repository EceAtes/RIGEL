package com.example.rigel_v1.domain;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class FeedbackUser extends Users{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    @JoinColumn(name = "feedback_user_id")
    private Map<Long, Student> students = new HashMap<>();

    public FeedbackUser() {
    }

    public FeedbackUser(Map<Long, Student> students) {
        this.students = students;
    }

    public FeedbackUser(String name, String email, String password, boolean notificationToMail, Role role, Department department, Map<Long, Student> students) {
        super(name, email, password, notificationToMail, role, department);
        this.students = students;
    }

    public FeedbackUser(String name, String email, String password, boolean notificationToMail, Role role, Department department, Set<Notification> notification, Map<Long, Student> students) {
        super(name, email, password, notificationToMail, role, department, notification);
        this.students = students;
    }

    public void addStudent(Student student){
        this.students.put(student.getId(), student);
    }

    public Map<Long, Student> getStudents() {
        return students;
    }

    public void setStudents(Map<Long, Student> students) {
        this.students = students;
    }

}
