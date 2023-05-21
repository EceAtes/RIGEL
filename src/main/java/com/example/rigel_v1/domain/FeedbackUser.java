package com.example.rigel_v1.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

//@Document("Administrations")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class FeedbackUser extends Users{

    @OneToMany
    @JoinColumn(name = "feedback_user_id")
    private Map<Long, Student> students = new HashMap<>();

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

}