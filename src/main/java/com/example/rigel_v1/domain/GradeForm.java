package com.example.rigel_v1.domain;

import lombok.*;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Document("Administrations")
@Entity
@Getter @Setter
public class GradeForm extends Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;   
    
    //  PART A
    private String companyName;
    private int studentScore;
    private boolean isRelated;
    private boolean isSupervisorEngineer;

    // PART B
    private boolean willBeRevised;

    // PART C
    private boolean isSatisfactory;
    private boolean isSigned;



    public GradeForm(){
        studentScore = 0;
        isRelated = false;
        isSupervisorEngineer = false;
        willBeRevised = true;
        isSatisfactory = false;
    }

}
