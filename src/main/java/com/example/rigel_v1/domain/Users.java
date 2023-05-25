package com.example.rigel_v1.domain;


import com.example.rigel_v1.domain.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

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

//@Document("Administrations")
@Entity
@Setter @Getter @NoArgsConstructor
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
    private Role role = Role.NOT_REGISTERED;

    @ManyToOne
    private Department department;

    public Users(String name, String email, String password, boolean notificationToMail, Role role, Department department) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.notificationToMail = notificationToMail;
        this.role = role;
        this.department = department;
    }

    public boolean deleteUser(){
        return true;
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
                '}';
    }
}
