package com.example.rigel_v1.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class GradeForm extends Report {
    private boolean willBeRevised;

    @OneToOne
    private StudentCourse course;        

    public GradeForm( StudentCourse course ){ 
        super(course);
        willBeRevised = true;
    }

    public void editGradeForm(){
    }
}
