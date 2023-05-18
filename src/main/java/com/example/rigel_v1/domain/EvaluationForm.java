package com.example.rigel_v1.domain;

import java.io.File;
import com.example.rigel_v1.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter @Getter @NoArgsConstructor
public class EvaluationForm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;   
    
    private int studentScore;
    private boolean isRelated;
    private boolean isSupervisorEngineer;
    private boolean isSatisfactory;
    private Recommendation recommendation;
    private String companyName;

    @OneToOne
    StudentCourse course;

    public EvaluationForm(int studentScore, boolean isRelated, boolean isSupervisorEngineer, boolean isSatisfactory, Recommendation recommendation, String companyName, StudentCourse course) {
        this.studentScore = studentScore;
        this.isRelated = isRelated;
        this.isSupervisorEngineer = isSupervisorEngineer;
        this.isSatisfactory = isSatisfactory;
        this.recommendation = recommendation;
        this.companyName = companyName;
        this.course = course;
    }

    /*public File toPDF(){
        return File(test.pdf);
    }*/
}
