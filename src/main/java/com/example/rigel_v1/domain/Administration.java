package com.example.rigel_v1.domain;


import jakarta.persistence.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Administration extends Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(mappedBy = "administrators")
    private Map<Integer, CriteriaReport> criteriaReports =new HashMap<>();//maybe we can give sections id's?

    //private Statistics statistics;
    private File eSignature = null;


    public Administration() {
    }



    public Administration(Map<Integer, CriteriaReport> criteriaReports, File eSignature) { //, Statistics statistics
        this.criteriaReports = criteriaReports;
        //this.statistics = statistics;
        this.eSignature = eSignature;
    }

    public Administration(String name, String email, String password, boolean notificationToMail, Department department, Map<Integer, CriteriaReport> criteriaReports, Statistics statistics, File eSignature) {
        super(name, email, password, notificationToMail, Role.ADMINISTRATION, department);
        this.criteriaReports = criteriaReports;
        //this.statistics = statistics;
        this.eSignature = eSignature;
    }

    public Administration(String name, String email, String password, boolean notificationToMail, Department department, Set<Notification> notification, Map<Integer, CriteriaReport> criteriaReports, File eSignature) { //, Statistics statistics
        super(name, email, password, notificationToMail, Role.ADMINISTRATION, department, notification);
        this.criteriaReports = criteriaReports;
        //this.statistics = statistics;
        this.eSignature = eSignature;
    }

    //Rest is for secretary
    public Administration(String name, String email, String password, boolean notificationToMail, Role role, Department department) {
        super(name, email, password, notificationToMail, role, department);
    }

    public Administration(String name, String email, String password, boolean notificationToMail, Role role, Department department, Map<Integer, CriteriaReport> criteriaReports, File eSignature) { //, Statistics statistics
        super(name, email, password, notificationToMail, role, department);
        this.criteriaReports = criteriaReports;
        //this.statistics = statistics;
        this.eSignature = eSignature;
    }

    public Administration(String name, String email, String password, boolean notificationToMail, Role role, Department department, Set<Notification> notification, Map<Integer, CriteriaReport> criteriaReports, File eSignature) {//, Statistics statistics
        this.criteriaReports = criteriaReports;
        //this.statistics = statistics;
        this.eSignature = eSignature;
    }

    public Map<Integer, CriteriaReport> getCriteriaReports() {
        return criteriaReports;
    }

    public void setCriteriaReports(Map<Integer, CriteriaReport> criteriaReports) {
        this.criteriaReports = criteriaReports;
    }

    /*
        public Statistics getStatistics() {
            return statistics;
        }

        public void setStatistics(Statistics statistics) {
            this.statistics = statistics;
        }
    */
    public File geteSignature() {
        return eSignature;
    }

    public void seteSignature(File eSignature) {
        this.eSignature = eSignature;
    }
}
