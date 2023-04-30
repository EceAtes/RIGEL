package com.example.rigel_v1.domain;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.example.rigel_v1.repositories.InstructorRepository;
import jakarta.persistence.*;


enum Score{
    satisfactory, 
    unsatisfactory
}

enum Status {
    waitingInstructorAppointment,
    uploadReport,
    waitingSummerTrainingEvaluationFromCompany,
    waitingInstructorEvaluation,
    uploadRevision,
    waitingFinalConfirmation,
    gradeSatisfactory,
    gradeUnsatisfactory,
    withdrawn
} 



@Entity
public class StudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                
    
    private Date deadline;                                  
    private Score score;
    private Status status;
    private Report.CourseName courseName;

    @ManyToOne
    private Student courseTaker;

    @ManyToOne
    private Instructor instructor;

    @OneToMany
    @JoinColumn(name = "course_id")
    private List<InternshipReport> internshipReports;
    
    @OneToOne
    private GradeForm gradeForm;

    @OneToOne
    private CriteriaReport criteriaReport;

    @OneToOne
    private EvaluationForm evaluationForm;

    public StudentCourse(){
    }

    public StudentCourse(Student courseTaker, Report.CourseName courseName){   
        this.courseTaker = courseTaker;
        this.courseName = courseName;  
        internshipReports = new LinkedList<>();   
        gradeForm = new GradeForm(false, courseName, this, ReportStatus.changable);
        criteriaReport = new CriteriaReport(false, courseName, this, ReportStatus.changable);
        evaluationForm = new EvaluationForm();
        //courseTaker.enrollCourse(this);
    }

    public StudentCourse(Student courseTaker, Report.CourseName courseName, Instructor instructor){      
        this.courseTaker = courseTaker;
        this.courseName = courseName;  
        this.instructor = instructor;
        internshipReports = new LinkedList<>();   
        gradeForm = new GradeForm(false, courseName, this, ReportStatus.changable);
        criteriaReport = new CriteriaReport(false, courseName, this, ReportStatus.changable);
        evaluationForm = new EvaluationForm();
        //courseTaker.enrollCourse(this);
        //instructor.addCourse(this);  
        //instructor.addStudent(courseTaker); // not filled yet
    }

    public void uploadInternshipReport(File report){ 
        System.out.println("internship report of " + courseTaker.getName() + " && " + courseName + " uploaded");
    }
    
    public void uploadGradeForm(GradeForm report){ 
    }

    public void uploadCriteriaReport(CriteriaReport report){ 
    }

    public void uploadEvaluationForm(EvaluationForm report){ 
    }

    public InternshipReport confirmInternshipReport(){ 
        return internshipReports.get(0);        // last iteration
    }
    
    public GradeForm confirmGradeForm(){
        return gradeForm;
    }

    public CriteriaReport confirmCriteriaReport(){
        return criteriaReport;
    }

    public EvaluationForm confirmEvaluationForm(){
        return evaluationForm;
    }

    public List<InternshipReport> getInternshipReports(){
        return internshipReports;
    }

    public InternshipReport getFinalInternshipReport(){
        return internshipReports.get(0);        // last iteration
    }
    
    public GradeForm getGradeForm(){
        return gradeForm;
    }

    public CriteriaReport getCriteriaReport(){
        return criteriaReport;
    }

    public EvaluationForm getEvaluationForm(){
        return evaluationForm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Report.CourseName getCourseName() {
        return courseName;
    }

    public void setCourseName(Report.CourseName courseName) {
        this.courseName = courseName;
    }

    public Instructor getInstructor() {
        return instructor;
    }
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Date getDeadline() {
        return deadline;
    }
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Score getScore() {
        return score;
    }
    public void setScore(Score score) {
        this.score = score;
    }    

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public Student getCourseTaker() {
        return courseTaker;
    }

    public void setCourseTaker(Student courseTaker) {
        this.courseTaker = courseTaker;
        instructor.addStudent(courseTaker);
        instructor.addCourse(this);
    }

    public void setInternshipReports(List<InternshipReport> internshipReports) {
        this.internshipReports = internshipReports;
    }

    public void setGradeForm(GradeForm gradeForm) {
        this.gradeForm = gradeForm;
    }

    public void setCriteriaReport(CriteriaReport criteriaReport) {
        this.criteriaReport = criteriaReport;
    }

    public void setEvaluationForm(EvaluationForm evaluationForm) {
        this.evaluationForm = evaluationForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentCourse course = (StudentCourse) o;

        return courseName.equals(course.courseName) && instructor.equals(course.instructor);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", instructor='" + instructor + '\'' +
                ", status=" + status +
                ", score=" + score +
                '}';
    }
}
