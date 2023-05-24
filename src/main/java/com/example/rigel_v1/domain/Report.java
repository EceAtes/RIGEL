package com.example.rigel_v1.domain;

import com.example.rigel_v1.domain.enums.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Report {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private ReportStatus reportStatus;

    public Report( StudentCourse studentCourse ) {
        this.reportStatus = ReportStatus.saved;
    }

    public void toPDF(){        
    }

    /*
    public boolean deleteReport(){
        return true;
    }

    public boolean saveReport(){
        return true;
    }

    public boolean submitReport(){
        return true;
    }*/
}