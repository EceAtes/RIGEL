package com.example.rigel_v1.domain;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.example.rigel_v1.domain.enums.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Setter @Getter @NoArgsConstructor
public class StudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("name")
    private CourseName courseName;

    //  @JsonProperty("student")
    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonBackReference
    private Student courseTaker;

    @ManyToOne
    @JsonBackReference
    private Instructor instructor;

    @OneToMany
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private List<InternshipReport> internshipReports = new ArrayList<>();
    private String internshipReportFolderKey = "";
    private int iterationCount = 0;

    @OneToOne
    @JsonBackReference
    private CriteriaReport criteriaReport;

    @OneToMany
    @JoinColumn(name = "course_id")
    private List<GradeForm> gradeForms = new ArrayList<>();
    private String gradeFormFolderKey;

    @JsonProperty("score")
    private Score score = Score.not_determined;

    //  PART A
    private String companyName;
    private int companyScore = -1;
    private boolean isRelated;
    private boolean isSupervisorEngineer;

    private LocalDate internshipReportUploadDeadline;

    @JsonProperty("status")
    private Status status = Status.waitingSummerTrainingEvaluationFromCompany;

    public StudentCourse(Student courseTaker, CourseName courseName){   
        this.courseTaker = courseTaker;
        this.courseName = courseName;
        this.instructor = null;
        internshipReports = new LinkedList<>();   
        internshipReportFolderKey = "none";
        iterationCount = 0;
        companyScore = -1;
        //criteriaReport = new CriteriaReport();
        gradeForms = new LinkedList<>();
        this.status = Status.waitingSummerTrainingEvaluationFromCompany;
        this.internshipReportUploadDeadline = LocalDate.now().plusDays(90); //explanation
    }

    public StudentCourse(Student courseTaker, CourseName courseName, Instructor instructor) {
        this.courseTaker = courseTaker;
        this.courseName = courseName;
        this.instructor = instructor;
        internshipReports = new LinkedList<>();   
        internshipReportFolderKey = "none";
        iterationCount = 0;
        companyScore = -1;
        criteriaReport = new CriteriaReport();
        gradeForms = new LinkedList<>();
        this.status = Status.waitingSummerTrainingEvaluationFromCompany;
    }

    public void uploadInternshipReport(InternshipReport rep) {
        internshipReports.add(rep);
        System.out.println("internship report of " + courseTaker.getName() + " && " + courseName + " uploaded");
        this.status = Status.waitingInstructorEvaluation;
    }

     //might be empty    
    public InternshipReport getLastInternshipReportSubmitted(){
        return internshipReports.get(internshipReports.size()-1);   // last iteration
    }
    
    //might be empty
    public GradeForm getLastGradeReport(){
        return gradeForms.get(gradeForms.size()-1);        // last iteration
    }

    public void uploadCriteriaReport(CriteriaReport report) {
        this.criteriaReport = report;
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
        return "StudentCourse{" +
                "id=" + id +
                ", courseName=" + courseName +
                ", status=" + status +
                '}';
    }
}
