package com.example.rigel_v1.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class CriteriaReport extends Report{
    
    @OneToMany
    @JoinColumn(name = "criteriareport_id")
    private List<Question> questions;

    private boolean isCompleted;

    public CriteriaReport(){
    }

    public CriteriaReport(boolean isSatisfactory, CourseName courseName, Student student, Instructor evaluator, ReportStatus reportStatus){
        super(isSatisfactory, courseName, student, evaluator, reportStatus);
        questions = new ArrayList<Question>();
        isCompleted = false;
    }

    public void addQuestion(Question question){
    }

    public void editCriteriaForm(){
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
