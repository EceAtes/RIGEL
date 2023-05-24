package com.example.rigel_v1.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class InternshipReport extends Report{

    private String reportLink;

    @ManyToOne
    private Student ownerStudent;

    //private String description;
    private boolean TA_check;

    @OneToMany
    private List<Feedback> TA_Feedback;

    @OneToMany
    private List<Feedback> instructorFeedback;

    public InternshipReport( StudentCourse course, Student ownerStudent, String reportLink){ // String description
        super(course);
        // this.description = description;
        this.ownerStudent = ownerStudent;
        this.reportLink = reportLink;
        TA_Feedback = new ArrayList<Feedback>();
        instructorFeedback = new ArrayList<Feedback>();
        TA_check = false;
    }


    void giveFeedback(int id, Feedback feedback){ 
    }

    public void deleteFeedback(int id){
    }

}