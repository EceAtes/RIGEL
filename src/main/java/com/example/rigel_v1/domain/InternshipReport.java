package com.example.rigel_v1.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class InternshipReport extends Report{

    private String description;
    private boolean TA_check;

    @OneToMany
    @JoinColumn(name = "internshipreport_id")
    private List<Feedback> TA_Feedback;

    @OneToMany
    @JoinColumn(name = "internshipreport_id")
    private List<Feedback> instructorFeedback;

    InternshipReport(){
    }

    InternshipReport(boolean isSatisfactory, CourseName courseName, Student student, Instructor evaluator, ReportStatus reportStatus, String description){
        super(isSatisfactory, courseName, student, evaluator, reportStatus);
        TA_Feedback = new ArrayList<Feedback>();
        instructorFeedback = new ArrayList<Feedback>();
        TA_check = false;
        this.description = description;
    }
    
    void giveFeedback(int id, Feedback feedback){ 
    }

    public List<Feedback> getTA_Feedback() {
        return TA_Feedback;
    }

    public List<Feedback> getInstructorFeedback() {
        return instructorFeedback;
    }

    public void deleteFeedback(int id){
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getTA_check() {
        return TA_check;
    }

    public void setTA_check(boolean tA_check) {
        TA_check = tA_check;
    }

}
