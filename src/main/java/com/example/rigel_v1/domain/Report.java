package com.example.rigel_v1.domain;

import com.example.rigel_v1.domain.enums.*;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Document("Administrations")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Report {

 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private boolean isSatisfactory;
    private CourseName courseName;
    private ReportStatus reportStatus;

    //@OneToOne
    //private Student student;        
    
    //@OneToOne
    //private Instructor evaluator;

    public Report(boolean isSatisfactory, CourseName courseName,  ReportStatus reportStatus) { //Student student, Instructor evaluator,
        this.isSatisfactory = isSatisfactory;
        this.courseName = courseName;
        //this.student = student;
        //this.evaluator = evaluator;
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

    public void setSatisfaction(boolean b) {
        this.isSatisfactory = b;
    }
}