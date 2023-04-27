package com.example.rigel_v1.domain;

import java.io.File;
import java.util.Date;
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
public class Course {
    
    public enum CourseCode{
        _299,
        _399
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                
    
    private Date deadline;                                  
    private Score score;
    private Status status;
    private CourseCode courseCode;

    @ManyToOne
    private Student courseTaker;

    @OneToMany
    @JoinColumn(name = "intern_report_id")
    private List<InternshipReport> internshipReports;
    
    @ManyToOne
    private Instructor instructor;

    @OneToOne
    private GradeForm gradeForm;

    @OneToOne
    private CriteriaReport criteriaReport;

    @OneToOne
    private EvaluationForm evaluationForm;

    public Course(){
    }

    public Course(CourseCode courseCode){        
        this.courseCode = courseCode;
    }

    public Course(CourseCode courseCode, Instructor instructor){       
        this.courseCode = courseCode;
        this.instructor = instructor;
        instructor.addCourse(this);
    }

    public void uploadInternshipReport(File report){ 
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

    public CourseCode getCourseCode() {
        return courseCode;
    }
    
    public void setCourseCode(CourseCode courseCode) {
        this.courseCode = courseCode;
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

        Course course = (Course) o;

        return courseCode.equals(course.courseCode) && instructor.equals(course.instructor);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseCode='" + courseCode + '\'' +
                ", instructor='" + instructor + '\'' +
                ", status=" + status +
                ", score=" + score +
                '}';
    }
}

    
