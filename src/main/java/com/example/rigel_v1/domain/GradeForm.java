package com.example.rigel_v1.domain;

import java.util.ArrayList;
import java.util.Date;

import jakarta.persistence.*;

@Entity
public class GradeForm extends Report {
    private boolean willBeRevised;
    //private Date dueDate;           // ??????????

    @OneToMany
    @JoinColumn(name = "gradeform_id")
    private ArrayList<Question> questions;

    public GradeForm(){
    }

    public GradeForm(boolean isSatisfactory, CourseName courseName, Student student, Instructor evaluator, ReportStatus reportStatus){
        super(isSatisfactory, courseName, student, evaluator, reportStatus);
        willBeRevised = true;
        questions = new ArrayList<Question>();
    }

    public void editGradeForm(){
    }

    public boolean getRevisionStatus() {
        return willBeRevised;
    }

    public void setRevisionStatus(boolean willBeRevised) {
        this.willBeRevised = willBeRevised;
    }

    /*public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }*/

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }


}
