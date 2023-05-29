package com.example.rigel_v1.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.example.rigel_v1.domain.enums.*;
import lombok.*;

@Entity
@Getter @Setter
public class CriteriaReport extends Report{

    @OneToMany
    private List<Question> questions;

    private boolean isCompleted;

    private Recommendation recommendation;

    public CriteriaReport(){
        super();
        this.questions = new ArrayList<Question>();
        this.isCompleted = false;
        recommendation = Recommendation.satisfactory;
    }

    public CriteriaReport(List<Question> questions){
        this.questions = questions;
        this.isCompleted = false;
        recommendation = Recommendation.satisfactory;
    }

    public int scoreOfItemOne(){
        return questions.get(0).getScore();
    }

    public int sumOfItemsFromTwoToSeven(){
        int sum = 0;
        for(int i = 0; i < 7; i++ ){
            sum = sum + questions.get(i).getScore();
        }
        return sum;
    }

    public int scoreOfEvaluationOfTheReport(){
        return questions.get(6).getScore();
    }

    public boolean isOverallSatisfactory(){
        return scoreOfItemOne() >= 7 &&
               scoreOfEvaluationOfTheReport() >= 30 &&
               scoreOfEvaluationOfTheReport() >= 7;

    }
}
