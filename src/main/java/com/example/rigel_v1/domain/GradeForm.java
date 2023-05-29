package com.example.rigel_v1.domain;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter @Setter
public class GradeForm extends Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;   
    
    // will be set aoutomatically after criteria report submitted
    String generatedFormKey;

    // PART B
    private boolean willBeRevised;

}
