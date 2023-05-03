package com.example.rigel_v1.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.rigel_v1.domain.enums.*;

import jakarta.persistence.*;

@Entity
public class InternshipReport extends Report{

    @ManyToOne
    private StudentCourse course;

    @ManyToOne
    private Student ownerStudent;

    private String description;
    private boolean TA_check;

    @OneToMany
    @JoinColumn(name = "internshipreport_id")
    private List<Feedback> TA_Feedback;

    @OneToMany
    @JoinColumn(name = "internshipreport_id")
    private List<Feedback> instructorFeedback;

    public InternshipReport(){
    }

    public InternshipReport(boolean isSatisfactory, CourseName courseName, StudentCourse course, Student ownerStudent, ReportStatus reportStatus, String description){ // Instructor evaluator,
        super(isSatisfactory, courseName, reportStatus);
        this.description = description;
        this.course = course;
        this.ownerStudent = ownerStudent;
        TA_Feedback = new ArrayList<Feedback>();
        instructorFeedback = new ArrayList<Feedback>();
        TA_check = false;
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

    public StudentCourse getCourse() {
        return course;
    }

    public void setCourse(StudentCourse course) {
        this.course = course;
    }

    public Student getOwnerStudent() {
        return ownerStudent;
    }

    public void setOwnerStudent(Student ownerStudent) {
        this.ownerStudent = ownerStudent;
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