package com.example.rigel_v1.domain;

import jakarta.persistence.*;

//@Document("Administrations")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String feedback;


    @OneToOne
    private FeedbackUser givenBy;

    public Feedback(String feedback, FeedbackUser givenBy) {
        this.feedback = feedback;
        this.givenBy = givenBy;
    }

    public boolean deleteFeedback(){
        return true;
    }
    
}