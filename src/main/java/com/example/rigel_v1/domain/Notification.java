package com.example.rigel_v1.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.TypeAlias;

import java.time.LocalDateTime;
import java.util.Date;

enum Priority {
    HIGH,
    MEDIUM,
    LOW
}

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL}, targetEntity = Users.class)
    @JoinColumn(name="user_id", nullable=false)
    private Users sender;

    private LocalDateTime date;
    private int receiverID;
    private String message;
    private Priority priority;
    private String title;


    public Notification(int receiverID, String message, Priority priority, String title) {
        this.date = LocalDateTime.now();
        this.receiverID = receiverID;
        this.message = message;
        this.priority = priority;
        this.title = title;
    }

    public Notification() {

    }

    public Users getSender() {
        return sender;
    }

    public void setSender(Users sender) {
        this.sender = sender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
