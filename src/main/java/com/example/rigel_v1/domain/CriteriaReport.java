package com.example.rigel_v1.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class CriteriaReport extends Report{
    
    @OneToMany
    @JoinColumn(name = "criteriareport_id")
    private List<Question> questions;

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
