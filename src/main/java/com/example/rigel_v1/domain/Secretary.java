package com.example.rigel_v1.domain;

import jakarta.persistence.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//@Document("Administrations")
@Entity
public class Secretary extends Administration{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    @JoinColumn(name = "secretary_id")
    private Map<Integer, EvaluationForm> EvaluationForm;//are there multiple secretaries? ;-;

    @OneToMany
    @JoinColumn(name = "secretary_id")
    private Map<Integer, GradeForm> gradeForms;

    public Secretary() {
    }

    public Secretary(String name, String email, String password, boolean notificationToMail, Department department) {
        super(name, email, password, notificationToMail, Role.SECRETARY, department);
        this.EvaluationForm = new HashMap<>();
        this.gradeForms = new HashMap<>();
    }

    public Secretary(Map<Integer, CriteriaReport> criteriaReports, File eSignature, Map<Integer, EvaluationForm> EvaluationForm, Map<Integer, GradeForm> gradeForms) {//, Statistics statistics
        super(criteriaReports, eSignature);//, statistics
        this.EvaluationForm = EvaluationForm;
        this.gradeForms = gradeForms;
    }

    public Secretary(String name, String email, String password, boolean notificationToMail, Department department, Map<Integer, CriteriaReport> criteriaReports, File eSignature, Map<Integer, EvaluationForm> EvaluationForm, Map<Integer, GradeForm> gradeForms) {//, Statistics statistics
        super(name, email, password, notificationToMail, Role.SECRETARY, department, criteriaReports, eSignature);//, statistics
        this.gradeForms = gradeForms;
    }

    public Secretary(String name, String email, String password, boolean notificationToMail, Department department, Set<Notification> notification, Map<Integer, CriteriaReport> criteriaReports, File eSignature, Map<Integer, EvaluationForm> EvaluationForm, Map<Integer, GradeForm> gradeForms) {//, Statistics statistics
        super(name, email, password, notificationToMail, Role.SECRETARY, department, notification, criteriaReports, eSignature);//, statistics
        this.EvaluationForm = EvaluationForm;
        this.gradeForms = gradeForms;
    }

    public Map<Integer, EvaluationForm> getEvaluationForm() {
        return EvaluationForm;
    }

    public void setEvaluationForm(Map<Integer, EvaluationForm> EvaluationForm) {
        this.EvaluationForm = EvaluationForm;
    }

    public Map<Integer, GradeForm> getGradeForms() {
        return gradeForms;
    }

    public void setGradeForms(Map<Integer, GradeForm> gradeForms) {
        this.gradeForms = gradeForms;
    }
}
