package com.example.rigel_v1.domain;

import java.io.File;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Instructor extends FeedbackUser{

    private Map<Integer, Student> graded;
    private Map<Integer, Student> toBeGraded;
    private File eSignature;
    private Statistics statistics;

    public Instructor() {
    }

    public Instructor(Map<Integer, Student> students, Map<Integer, Student> graded, Map<Integer, Student> toBeGraded, File eSignature, Statistics statistics) {
        super(students);
        this.graded = graded;
        this.toBeGraded = toBeGraded;
        this.eSignature = eSignature;
        this.statistics = statistics;
    }

    public Instructor(String name, String email, String password, boolean notificationToMail, Department department, Map<Integer, Student> students, Map<Integer, Student> graded, Map<Integer, Student> toBeGraded, File eSignature, Statistics statistics) {
        super(name, email, password, notificationToMail, Role.INSTRUCTOR, department, students);
        this.graded = graded;
        this.toBeGraded = toBeGraded;
        this.eSignature = eSignature;
        this.statistics = statistics;
    }

    public Instructor(String name, String email, String password, boolean notificationToMail, Department department, Set<Notification> notification, Map<Integer, Student> students, Map<Integer, Student> graded, Map<Integer, Student> toBeGraded, File eSignature, Statistics statistics) {
        super(name, email, password, notificationToMail, Role.INSTRUCTOR, department, notification, students);
        this.graded = graded;
        this.toBeGraded = toBeGraded;
        this.eSignature = eSignature;
        this.statistics = statistics;
    }

    public Map<Integer, Student> getGraded() {
        return graded;
    }

    public void setGraded(Map<Integer, Student> graded) {
        this.graded = graded;
    }

    public Map<Integer, Student> getToBeGraded() {
        return toBeGraded;
    }

    public void setToBeGraded(Map<Integer, Student> toBeGraded) {
        this.toBeGraded = toBeGraded;
    }

    public File geteSignature() {
        return eSignature;
    }

    public void seteSignature(File eSignature) {
        this.eSignature = eSignature;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
