package com.example.rigel_v1.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Entity
public class CriteriaReport extends Report{
    
    @OneToMany
    @JoinColumn(name = "criteria_report_id")
    private List<Question> questions;

    @ManyToMany
    @JoinTable(name = "criteria_administration", joinColumns = @JoinColumn(name = "criteria_report_id"),
            inverseJoinColumns = @JoinColumn(name = "administration_id"))
    private Map<String, Administration> administrators;

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
