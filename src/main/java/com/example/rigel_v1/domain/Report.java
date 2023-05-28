package com.example.rigel_v1.domain;

import com.example.rigel_v1.domain.enums.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Document("Administrations")
@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private ReportStatus reportStatus;

    public Report() {
        this.reportStatus = ReportStatus.saved;
    }
}