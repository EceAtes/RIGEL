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

@Entity
@Getter @Setter @NoArgsConstructor
public class InternshipReport extends Report{

    private String reportKey = "";
    private String description = "";
    private boolean TA_check;

    @ManyToOne
    @JsonBackReference
    private Student ownerStudent;

    @OneToMany
    private List<Feedback> feedbacks;

    public InternshipReport(Student ownerStudent, String reportKey, String description){
        super();
        this.ownerStudent = ownerStudent;
        this.reportKey = reportKey;
        feedbacks = new ArrayList<Feedback>();
        TA_check = false;
        this.description = description;
    }

}