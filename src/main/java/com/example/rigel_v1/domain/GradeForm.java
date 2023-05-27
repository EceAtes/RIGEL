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
    
    String generatedFormKey;

    // PART B
    private boolean willBeRevised;

}
