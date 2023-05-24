package com.example.rigel_v1.domain;

import java.io.File;
import java.util.*;

import com.example.rigel_v1.domain.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@Setter @Getter @NoArgsConstructor
public class Instructor extends FeedbackUser{

    @OneToMany
    @JoinColumn(name = "instructor_id")
    private List<Section> sections;

    @OneToMany
    @JoinColumn(name = "instructor_id")
    private List<StudentCourse> graded = new LinkedList<>();

    @OneToMany
    @JoinColumn(name = "instructor_id")
    private List<StudentCourse> toBeGraded = new LinkedList<>();

    private File eSignature;

    //private Statistics statistics;

    public Instructor(String name, String email, String password, boolean notificationToMail, Department department) {
        super(name, email, password, notificationToMail, Role.INSTRUCTOR, department, new HashMap<>());
        department.addInstructor(this);
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