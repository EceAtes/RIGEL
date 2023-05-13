package com.example.rigel_v1.domain;

import java.io.File;
import java.util.*;

import com.example.rigel_v1.NullKeySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Instructor extends FeedbackUser{

    @OneToMany
    @JoinColumn(name = "instructor_id")
    private List<Section> sections = new LinkedList<>();

    @OneToMany
    @JoinColumn(name = "instructor_id")
    private List<StudentCourse> graded = new LinkedList<>();

    @OneToMany
    @JoinColumn(name = "instructor_id")
    private List<StudentCourse> toBeGraded = new LinkedList<>();

    private File eSignature = new File("pom.xml");

    //private Statistics statistics;


    public Instructor() {
    }

    public Instructor(String name, String email, String password, boolean notificationToMail, Department department) {
        super(name, email, password, notificationToMail, Role.INSTRUCTOR, department, new HashMap<>());
        //department.addInstructor(this);
        eSignature = new File("pom.xml");
    }

    public Instructor(Map<Long, Student> students, List<StudentCourse> graded, List<StudentCourse> toBeGraded, File eSignature) { //, Statistics statistics) {
        super(students);
        this.graded = graded;
        this.toBeGraded = toBeGraded;
        this.eSignature = eSignature;
        //this.statistics = statistics;
        this.sections = new ArrayList<>();
    }

    public Instructor(String name, String email, String password, boolean notificationToMail, Department department, Map<Long, Student> students, List<StudentCourse> graded, List<StudentCourse> toBeGraded, File eSignature) {//, Statistics statistics) {
        super(name, email, password, notificationToMail, Role.INSTRUCTOR, department, students);
        this.graded = graded;
        this.toBeGraded = toBeGraded;
        this.eSignature = eSignature;
        //this.statistics = statistics;
        this.sections = new ArrayList<>();
        department.addInstructor(this);
    }

    public Instructor(String name, String email, String password, boolean notificationToMail, Department department, Set<Notification> notification, Map<Long, Student> students, List<StudentCourse> graded, List<StudentCourse> toBeGraded, File eSignature, List<Section> sections) { //Statistics statistics
        super(name, email, password, notificationToMail, Role.INSTRUCTOR, department, notification, students);
        this.graded = graded;
        this.toBeGraded = toBeGraded;
        this.eSignature = eSignature;
        //this.statistics = statistics;
        this.sections = sections;
    }

    public List<StudentCourse> getGraded() {
        return graded;
    }

    public void setGraded(List<StudentCourse> graded) {
        this.graded = graded;
    }

    public List<StudentCourse> getToBeGraded() {
        return toBeGraded;
    }

    public void setToBeGraded(List<StudentCourse> toBeGraded) {
        this.toBeGraded = toBeGraded;
    }

    public File geteSignature() {
        return eSignature;
    }

    public void seteSignature(File eSignature) {
        this.eSignature = eSignature;
    }

    public void addCourse(StudentCourse course){
        this.toBeGraded.add(course);
    }

    public void addStudent(Student student){
        //this.toBeGraded.add(sections); ????????????
    }

    /*
    public Statistics getStatistics() {
        return statistics;
    }
    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }*/
}