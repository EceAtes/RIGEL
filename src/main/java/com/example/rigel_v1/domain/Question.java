package com.example.rigel_v1.domain;

import jakarta.persistence.*;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;   

    private String question;
    private String answer;
    private String evidence;
    
    @ManyToOne
    private CriteriaReport criteriaReport;
    
    public Question(){
    }

    public Question(String question, String answer, String evidence){
        this.question = question;
        this.answer = answer;
        this.evidence = evidence;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }  
    
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getEvidence() {
        return evidence;
    }
    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public CriteriaReport getCriteriaReport() {
        return criteriaReport;
    }

    public void setCriteriaReport(CriteriaReport criteriaReport) {
        this.criteriaReport = criteriaReport;
    }


}
