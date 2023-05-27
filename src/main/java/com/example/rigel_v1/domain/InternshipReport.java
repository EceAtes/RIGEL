package com.example.rigel_v1.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Document("Administrations")
@Entity
@Getter @Setter @NoArgsConstructor
public class InternshipReport extends Report{

    private String reportLink;
    private String description;
    private boolean TA_check;

    @ManyToOne
    @JsonBackReference
    private Student ownerStudent;

    @OneToMany
    private List<Feedback> TA_Feedback;

    @OneToMany
    private List<Feedback> instructorFeedback;

    public InternshipReport(Student ownerStudent, String reportLink, String description){
        super();
        this.ownerStudent = ownerStudent;
        this.reportLink = reportLink;
        TA_Feedback = new ArrayList<Feedback>();
        instructorFeedback = new ArrayList<Feedback>();
        TA_check = false;
        description = "";
    }

    public void giveFeedback(Feedback feedback){ 
        TA_Feedback.add(feedback);
    }

}