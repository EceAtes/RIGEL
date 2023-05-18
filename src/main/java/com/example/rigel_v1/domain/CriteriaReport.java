package com.example.rigel_v1.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.rigel_v1.domain.enums.*;


@Entity
@Getter @Setter @NoArgsConstructor
public class CriteriaReport extends Report{
    
    @OneToMany
    private List<Question> questions;

    @ManyToMany //what is this
    @JoinTable(name = "criteria_administration", joinColumns = @JoinColumn(name = "criteria_report_id"),
            inverseJoinColumns = @JoinColumn(name = "administration_id"))
    private Map<String, Administration> administrators;

    private boolean isCompleted;

    public CriteriaReport( StudentCourse course ){
        super(course);
        this.questions = new ArrayList<Question>();
        this.administrators = new HashMap<String, Administration>();
        this.isCompleted = false;
    }

    public void generateQuestions(){
        Question quest1 = new Question("q1");
        Question quest2 = new Question("q1");
        Question quest3 = new Question("q1");
        questions.add(quest1);
        questions.add(quest2);
        questions.add(quest3);
    }

}
