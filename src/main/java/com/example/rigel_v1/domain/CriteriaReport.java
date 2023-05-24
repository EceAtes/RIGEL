package com.example.rigel_v1.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.*;

import com.example.rigel_v1.domain.enums.*;


@Entity
@Getter @Setter @NoArgsConstructor
public class CriteriaReport extends Report{
    
    @OneToMany
    private List<Question> questions;

    private boolean isCompleted;


    public CriteriaReport( StudentCourse course ){
        super(course);
        this.questions = new ArrayList<Question>();
        this.isCompleted = false;
    }
}
