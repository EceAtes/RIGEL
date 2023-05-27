package com.example.rigel_v1.domain;

import lombok.*;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Document("Administrations")
@Entity
@Getter @Setter @NoArgsConstructor
public class GradeForm extends Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;   
    
    // PART B
    private boolean willBeRevised;

    // PART C
    private boolean isSatisfactory;
    private boolean isSigned;

    @OneToOne // many to one????????????????????
    StudentCourse course;

    public GradeForm( StudentCourse course ){  
        super(course);
        willBeRevised = true;
        isSatisfactory = false;
    }

}
