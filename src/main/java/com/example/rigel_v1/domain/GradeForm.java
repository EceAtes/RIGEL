package com.example.rigel_v1.domain;

import java.util.ArrayList;
import java.util.Date;

import com.example.rigel_v1.domain.enums.*;

import jakarta.persistence.*;

@Entity
public class GradeForm extends Report {
    private boolean willBeRevised;
    //private Date dueDate;           // ??????????

    @OneToOne
    private StudentCourse course;        

    /*@OneToMany
    @JoinColumn(name = "gradeform_id")
    private ArrayList<Question> questions;*/

    public GradeForm(){
    }

    public GradeForm(boolean isSatisfactory, CourseName courseName, StudentCourse course,  ReportStatus reportStatus){ // Student student, Instructor evaluator,Student student, Instructor evaluator,
        super(isSatisfactory, courseName, reportStatus);
        willBeRevised = true;
        this.course = course;
        //questions = new ArrayList<Question>();
    }

    public void editGradeForm(){
    }

    public StudentCourse getCourse() {
        return course;
    }

    public void setCourse(StudentCourse course) {
        this.course = course;
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
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }*/


}
