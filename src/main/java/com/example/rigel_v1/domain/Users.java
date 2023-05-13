package com.example.rigel_v1.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.fasterxml.jackson.databind.jsontype.impl.TypeNameIdResolver;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("notifToMail")
    private boolean notificationToMail;
    private Role role;

    @ManyToOne
    private Department department;

    @OneToMany
    @JoinColumn(name = "users_id")
    private Set<Notification> notification = new HashSet<>();


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
        NOT_REGISTERED,
        ADMIN,
        STUDENT,
        TA,
        INSTRUCTOR,
        ADMINISTRATION,
        SECRETARY
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
                ", password='" + password + '\'' +
                ", notificationToMail=" + notificationToMail +
                ", role=" + role +
                ", department=" + department +
                ", notification=" + notification +
                '}';
    }
}
