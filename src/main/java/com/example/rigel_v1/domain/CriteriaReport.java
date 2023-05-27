package com.example.rigel_v1.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.*;

import com.example.rigel_v1.domain.enums.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Document("Administrations")
@Entity
@Getter @Setter @NoArgsConstructor
public class CriteriaReport extends Report{
    
    @OneToMany
    private List<Question> questions;

    private boolean isCompleted;

    private String generatedReportLink;

    private Recommendation recommendation;

    public CriteriaReport( StudentCourse course ){
        super(course);
        this.questions = new ArrayList<Question>();
        this.isCompleted = false;
    }

    public int scoreOfItemOne(){
        return questions.get(0).getScore();
    }

    public int sumOfItemsFromTwoToSeven(){
        int sum = 0;
        for(int i = 1; i < 7; i++ ){
            sum = sum + questions.get(0).getScore();
        }
        return sum;
    }

    public int scoreOfEvaluationOfTheReport(){
        return questions.get(7).getScore();
    }

    public boolean isOverallSatisfactory(){
        return scoreOfItemOne() >= 7 &&
               scoreOfEvaluationOfTheReport() >= 30 &&
               scoreOfEvaluationOfTheReport() >= 7;
    }
}
