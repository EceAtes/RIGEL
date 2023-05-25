package com.example.rigel_v1.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Document("Administrations")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;   

    private String question;
    private String answer;
    private int score;
    @ManyToOne
    @JsonBackReference
    private CriteriaReport criteriaReport;

    public Question(String question, String answer, int score) {
        this.question = question;
        this.answer = answer;
        this.score = score;

    }


}
