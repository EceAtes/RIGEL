package com.example.rigel_v1.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.rigel_v1.domain.enums.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Document("Administrations")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class InternshipReport extends Report{

    @ManyToOne
    //@JsonBackReference
    private StudentCourse course;

    @ManyToOne
    @JsonBackReference
    private Student ownerStudent;

    private String description;
    private boolean TA_check;

    @OneToMany
    @JoinColumn(name = "internshipreport_id")
    private List<Feedback> TA_Feedback;

    @OneToMany
    @JoinColumn(name = "internshipreport_id")
    private List<Feedback> instructorFeedback;

    public InternshipReport(boolean isSatisfactory, CourseName courseName, StudentCourse course, Student ownerStudent, String description){ // Instructor evaluator,
        super(courseName, ReportStatus.changable);
        this.description = description;
        this.course = course;
        this.ownerStudent = ownerStudent;
        TA_Feedback = new ArrayList<Feedback>();
        instructorFeedback = new ArrayList<Feedback>();
        TA_check = false;
    }


    void giveFeedback(int id, Feedback feedback){ 
    }

    public void deleteFeedback(int id){
    }
}