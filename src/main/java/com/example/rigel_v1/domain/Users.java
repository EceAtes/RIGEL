package com.example.rigel_v1.domain;


import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;
    private String password;
    private boolean notificationToMail;
    private Role role;



    @ManyToOne
    private Department department;

    @OneToMany
    @JoinColumn(name = "users_id") 
    private Set<Notification> notification;


    public Users() {
    }

    public Users(String name, String email, String password, boolean notificationToMail, Role role, Department department) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.notificationToMail = notificationToMail;
        this.role = role;
        this.department = department;
        this.notification = new HashSet<>();

    }

    public Users(String name, String email, String password, boolean notificationToMail, Role role, Department department, Set<Notification> notification) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.notificationToMail = notificationToMail;
        this.role = role;
        this.department = department;
        this.notification = notification;
    }

    public enum Role {
        SECRETARY,
        ADMIN,
        STUDENT,
        TA,
        INSTRUCTOR,
        ADMINISTRATION
    }

    public boolean deleteUser(){
        this.notification = null;
        return true;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isNotificationToMail() {
        return notificationToMail;
    }

    public void setNotificationToMail(boolean notificationToMail) {
        this.notificationToMail = notificationToMail;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    public Set<Notification> getNotification() {
        return notification;
    }

    public void setNotification(Set<Notification> notification) {
        this.notification = notification;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Users users = (Users) o;

        return id != null ? id.equals(users.id) : users.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", notificationToMail=" + notificationToMail +
                ", role=" + role +
                ", department=" + department +
                ", notification=" + notification +
                '}';
    }
}
