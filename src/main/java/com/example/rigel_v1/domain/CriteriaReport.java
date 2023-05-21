package com.example.rigel_v1.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.rigel_v1.domain.enums.*;

//@Document("Administrations")
@Entity
public class CriteriaReport extends Report{
    
    @OneToMany
    @JoinColumn(name = "criteria_report_id")
    private List<Question> questions;

    @ManyToMany
    @JoinTable(name = "criteria_administration", joinColumns = @JoinColumn(name = "criteria_report_id"),
            inverseJoinColumns = @JoinColumn(name = "administration_id"))
    private Map<String, Administration> administrators;

    @OneToOne
    private StudentCourse course;

    private boolean isCompleted;

    public CriteriaReport(){
    }

    public CriteriaReport(boolean isSatisfactory, CourseName courseName, StudentCourse course, ReportStatus reportStatus){ //Student student, Instructor evaluator,
        super(isSatisfactory, courseName, reportStatus);
        this.questions = new ArrayList<Question>();
        this.course = course;
        this.isCompleted = false;
    }

    public void addQuestion(Question question){
    }

    public void editCriteriaForm(){
    }

    public StudentCourse getCourse() {
        return course;
    }

    public void setCourse(StudentCourse course) {
        this.course = course;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

}
