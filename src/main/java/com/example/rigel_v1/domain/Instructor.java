package com.example.rigel_v1.domain;

import java.io.File;
import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Instructor extends FeedbackUser{

    @OneToMany
    @JoinColumn(name = "instructor_id")
    private List<Section> sections;

    @OneToMany
    @JoinColumn(name = "instructor_id")
    private List<Course> graded = new LinkedList<>();

    @OneToMany
    @JoinColumn(name = "instructor_id")
    private List<Course> toBeGraded = new LinkedList<>();

    private File eSignature;

    //private Statistics statistics;


    public Instructor() {
    }

    public Instructor(String name, String email, String password, boolean notificationToMail, Department department) {
        super(name, email, password, notificationToMail, Role.INSTRUCTOR, department, new HashMap<>());
    }

    public Instructor(Map<Long, Student> students, List<Course> graded, List<Course> toBeGraded, File eSignature) { //, Statistics statistics) {
        super(students);
        this.graded = graded;
        this.toBeGraded = toBeGraded;
        this.eSignature = eSignature;
        //this.statistics = statistics;
        this.sections = new ArrayList<>();
    }

    public Instructor(String name, String email, String password, boolean notificationToMail, Department department, Map<Long, Student> students, List<Course> graded, List<Course> toBeGraded, File eSignature) {//, Statistics statistics) {
        super(name, email, password, notificationToMail, Role.INSTRUCTOR, department, students);
        this.graded = graded;
        this.toBeGraded = toBeGraded;
        this.eSignature = eSignature;
        //this.statistics = statistics;
        this.sections = new ArrayList<>();
    }

    public Instructor(String name, String email, String password, boolean notificationToMail, Department department, Set<Notification> notification, Map<Long, Student> students, List<Course> graded, List<Course> toBeGraded, File eSignature, List<Section> sections) { //Statistics statistics
        super(name, email, password, notificationToMail, Role.INSTRUCTOR, department, notification, students);
        this.graded = graded;
        this.toBeGraded = toBeGraded;
        this.eSignature = eSignature;
        //this.statistics = statistics;
        this.sections = sections;
    }

    public List<Course> getGraded() {
        return graded;
    }

    public void setGraded(List<Course> graded) {
        this.graded = graded;
    }

    public List<Course> getToBeGraded() {
        return toBeGraded;
    }

    public void setToBeGraded(List<Course> toBeGraded) {
        this.toBeGraded = toBeGraded;
    }

    public File geteSignature() {
        return eSignature;
    }

    public void seteSignature(File eSignature) {
        this.eSignature = eSignature;
    }

    public void addCourse(Course course){
        this.toBeGraded.add(course);
    }

    /*
    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }*/
}
