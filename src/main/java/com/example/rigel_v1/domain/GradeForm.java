package com.example.rigel_v1.domain;

import java.util.ArrayList;
import java.util.Date;

import com.example.rigel_v1.domain.enums.*;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Document("Administrations")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class GradeForm extends Report {
    private boolean willBeRevised;
    //private Date dueDate;           // ??????????

    @OneToOne
    private StudentCourse course;        

    /*@OneToMany
    @JoinColumn(name = "gradeform_id")
    private ArrayList<Question> questions;*/


    public GradeForm(boolean isSatisfactory, CourseName courseName, StudentCourse course,  ReportStatus reportStatus){ // Student student, Instructor evaluator,Student student, Instructor evaluator,
        super(isSatisfactory, courseName, reportStatus);
        willBeRevised = true;
        this.course = course;
        //questions = new ArrayList<Question>();
    }

    public void editGradeForm(){
    }

}
