package com.example.rigel_v1.domain;

import jakarta.persistence.*;

//@Document("Administrations")
@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;   

    private String feedback;

    @OneToOne
    private FeedbackUser givenBy;

    public boolean deleteFeedback(){
        return true;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFeedback() {
        return feedback;
    }
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    
    public FeedbackUser getGivenBy() {
        return givenBy;
    }
    public void setGivenBy(FeedbackUser givenBy) {
        this.givenBy = givenBy;
    }
    
}
