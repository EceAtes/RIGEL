package com.example.rigel_v1.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;   

    private String question;
    private String answer;
    private String evidence;
    private double score;   

    @ManyToOne
    @JoinColumn(name = "criteria_report_id")
    private CriteriaReport criteriaReport;

    public Question(String question){
        this.question = question;
        this.answer = "";
        this.evidence = "";
        this.score = 0;
    }

}
