package com.example.rigel_v1.domain;

import java.io.File;

import com.example.rigel_v1.domain.enums.*;

import jakarta.persistence.*;

@Entity
public class EvaluationForm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;   
    
    private int studentScore;
    private boolean isRelated;
    private boolean isSupervisorEngineer;
    private boolean isSatisfactory;
    private Recommendation recommendation;
    private String companyName;

    @OneToOne
    StudentCourse course;
    
    public EvaluationForm(){
    }

    public EvaluationForm(int studentScore, boolean isRelated, boolean isSupervisorEngineer, boolean isSatisfactory, Recommendation recommendation, String companyName, StudentCourse course) {
        this.studentScore = studentScore;
        this.isRelated = isRelated;
        this.isSupervisorEngineer = isSupervisorEngineer;
        this.isSatisfactory = isSatisfactory;
        this.recommendation = recommendation;
        this.companyName = companyName;
        this.course = course;
    }


    /*public File toPDF(){
        return File(test.pdf);
    }*/

    public boolean deleteReport(){
        return true;
    }

    public boolean saveReport(){
        return true;
    }

    public boolean submitReport(){
        return true;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStudentScore() {
        return studentScore;
    }
    public void setStudentScore(int studentScore) {
        this.studentScore = studentScore;
    }
    public boolean isRelated() {
        return isRelated;
    }
    public void setRelated(boolean isRelated) {
        this.isRelated = isRelated;
    }
    public boolean isSupervisorEngineer() {
        return isSupervisorEngineer;
    }
    public void setSupervisorEngineer(boolean isSupervisorEngineer) {
        this.isSupervisorEngineer = isSupervisorEngineer;
    }
    public boolean getSatisfaction() {
        return isSatisfactory;
    }
    public void setSatisfaction(boolean isSatisfactory) {
        this.isSatisfactory = isSatisfactory;
    }
    public Recommendation getRecommendation() {
        return recommendation;
    }
    public void setRecommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
