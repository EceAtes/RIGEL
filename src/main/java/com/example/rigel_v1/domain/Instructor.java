package com.example.rigel_v1.domain;

import java.io.File;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.Entity;

@Entity
public class Instructor extends FeedbackUser{
/*
    private Map<Long, Student> graded;
    private Map<Long, Student> toBeGraded;
    private File eSignature;
    //private Statistics statistics;

    public Instructor() {
    }

    public Instructor(Map<Long, Student> students, Map<Long, Student> graded, Map<Long, Student> toBeGraded, File eSignature, Statistics statistics) {
        super(students);
        this.graded = graded;
        this.toBeGraded = toBeGraded;
        this.eSignature = eSignature;
    }

    public Instructor(String name, String email, String password, boolean notificationToMail, Department department, Map<Long, Student> students, Map<Long, Student> graded, Map<Long, Student> toBeGraded, File eSignature, Statistics statistics) {
        super(name, email, password, notificationToMail, Role.INSTRUCTOR, department, students);
        this.graded = graded;
        this.toBeGraded = toBeGraded;
        this.eSignature = eSignature;
    }

    public Instructor(String name, String email, String password, boolean notificationToMail, Department department, Set<Notification> notification, Map<Long, Student> students, Map<Long, Student> graded, Map<Long, Student> toBeGraded, File eSignature, Statistics statistics) {
        super(name, email, password, notificationToMail, Role.INSTRUCTOR, department, notification, students);
        this.graded = graded;
        this.toBeGraded = toBeGraded;
        this.eSignature = eSignature;
    }

    public Map<Long, Student> getGraded() {
        return graded;
    }

    public void setGraded(Map<Long, Student> graded) {
        this.graded = graded;
    }

    public Map<Long, Student> getToBeGraded() {
        return toBeGraded;
    }

    public void setToBeGraded(Map<Long, Student> toBeGraded) {
        this.toBeGraded = toBeGraded;
    }

    public File geteSignature() {
        return eSignature;
    }

    public void seteSignature(File eSignature) {
        this.eSignature = eSignature;
    }
*/
}