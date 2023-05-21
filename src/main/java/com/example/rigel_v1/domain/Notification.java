package com.example.rigel_v1.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.TypeAlias;

import java.time.LocalDateTime;
import java.util.Date;

enum Priority {
    HIGH,
    MEDIUM,
    LOW
}

//@Document("Administrations")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", sender=" + sender +
                ", date=" + date +
                ", receiverID=" + receiverID +
                ", message='" + message + '\'' +
                ", priority=" + priority +
                ", title='" + title + '\'' +
                '}';
    }

    //TO_DO
    public boolean deleteNotification(){
        return false;
    }
}
