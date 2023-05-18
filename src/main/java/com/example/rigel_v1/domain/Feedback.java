package com.example.rigel_v1.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;   

    private String feedback;

    @ManyToOne
    @JoinColumn(name = "internshipreport_id")
    private InternshipReport internshipreport;

    public boolean deleteFeedback(){
        return true;
    }
    
}
