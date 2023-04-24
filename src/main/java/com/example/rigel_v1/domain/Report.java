package com.example.rigel_v1.domain;

import jakarta.persistence.*;

enum ReportStatus{
    changable,
    unchangable
}

enum CourseName{
    CS299,
    CS399,
    ME299,
    ME399,
    IE299,
    IE399,
    EE299,
    EE399
}

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private boolean isSatisfactory;
    private CourseName courseName;
    private ReportStatus reportStatus;

    @OneToOne
    private Student student;        
    
    @OneToOne
    private Instructor evaluator;

    public Report(){
    }

    public Report(boolean isSatisfactory, CourseName courseName, Student student, Instructor evaluator, ReportStatus reportStatus) {
        this.isSatisfactory = isSatisfactory;
        this.courseName = courseName;
        this.student = student;
        this.evaluator = evaluator;
        this.reportStatus = reportStatus;
    }

    public void toPDF(){        
    }

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

    public void setSatisfaction(boolean isSatisfactory){
        this.isSatisfactory = isSatisfactory;
    }

    public boolean getSatisfaction(){
        return isSatisfactory;
    }

    public CourseName getCourseName() {
        return courseName;
    }

    public void setCourseName(CourseName courseName) {
        this.courseName = courseName;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Instructor getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(Instructor evaluator) {
        this.evaluator = evaluator;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }

}
