package com.example.rigel_v1.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.rigel_v1.domain.enums.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Document("Administrations")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class CriteriaReport extends Report{
    
    @OneToMany
    @JoinColumn(name = "criteria_report_id")
    private List<Question> questions;

    @ManyToMany
    @JoinTable(name = "criteria_administration", joinColumns = @JoinColumn(name = "criteria_report_id"),
            inverseJoinColumns = @JoinColumn(name = "administration_id"))
    private Map<String, Administration> administrators;

    @OneToOne
    @JsonBackReference
    private StudentCourse course;

    private boolean isCompleted;

    public CriteriaReport(boolean isSatisfactory, CourseName courseName, StudentCourse course, ReportStatus reportStatus){ //Student student, Instructor evaluator,
        super(isSatisfactory, courseName, reportStatus);
        this.questions = new ArrayList<Question>();
        this.course = course;
        this.isCompleted = false;
    }

    /*public void addQuestions(ArrayList<Question> questions){
        questions.addAll(questions);
    }*/

    public void addQuestion(Question question){
        questions.add(question);
    }

    public void editCriteriaForm(){
    }



}
