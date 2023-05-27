package com.example.rigel_v1.domain;

import java.io.File;
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
    private Instructor instructor;

    @OneToMany
    @JoinColumn(name = "course_id")
    private List<InternshipReport> internshipReports;
    private String internshipReportFolderKey;
    private int iterationCount = 0;
    //private Date internshipReportDeadline;
    
    @OneToOne
    private CriteriaReport criteriaReport;

    @OneToMany
    @JoinColumn(name = "course_id")
    private List<GradeForm> gradeForms;
    private String gradeFormFolderKey;

    //  PART A
    private String companyName;
    private int studentScore;
    private boolean isRelated;
    private boolean isSupervisorEngineer;

    @JsonProperty("status")
    private Status status;

    public StudentCourse(Student courseTaker, CourseName courseName){   
        this.courseTaker = courseTaker;
        this.courseName = courseName;
        this.instructor = null;
        internshipReports = new LinkedList<>();   
        internshipReportFolderKey = "none";
        iterationCount = 0;
        //criteriaReport = new CriteriaReport();
        gradeForms = new LinkedList<>();
        this.status = Status.waitingSummerTrainingEvaluationFromCompany;

        //gradeForm = new GradeForm(false, courseName, this, ReportStatus.changable);
        //criteriaReport = new CriteriaReport(false, courseName, this, ReportStatus.changable);
        //evaluationForm = new EvaluationForm();
        //courseTaker.enrollCourse(this);
    }

    public StudentCourse(Student courseTaker, CourseName courseName, Instructor instructor){      
        this.courseTaker = courseTaker;
        this.courseName = courseName;  
        this.instructor = instructor;
        internshipReports = new LinkedList<>();   
        internshipReportFolderKey = "none";
        iterationCount = 0;
        criteriaReport = new CriteriaReport();
        gradeForms = new LinkedList<>();
        this.status = Status.waitingSummerTrainingEvaluationFromCompany;
        studentScore = -1;
    }

    public void uploadInternshipReport(InternshipReport rep){
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

    public boolean generateGradeForm(){ 
        if(instructor.getESignature() != null){
            if(gradeForms.get(0).isWillBeRevised() 
               || (criteriaReport.getReportStatus() == ReportStatus.submitted)&&  studentScore != -1 )
                    System.out.println("You can create pdf file");
                    // create pdf!!
                    //
                    //
                    return true;
        }
        else{
            System.out.println("Cannot create grade form -- e-signature missing");
            return false;
        }
    }

    public boolean generateCriteriaReport(){ 
        if(instructor.getESignature() != null){         // NOT NECESSEARY I GUESS?????
            if(!gradeForms.get(0).isWillBeRevised() 
               || (criteriaReport.getReportStatus() == ReportStatus.submitted) &&  studentScore != -1 )
                    System.out.println("You can create pdf file");
                    // create pdf!!
                    //
                    //
                    return true;
        }
        else{
            System.out.println("Cannot create criteria report -- e-signature missing");
            return false;
        }
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
