package com.example.rigel_v1.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

//@Document("Administrations")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Instructor instructor;

    @ManyToMany
    @JoinTable(name = "section_student", joinColumns = @JoinColumn(name = "section_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Map<Integer, Student> students;
    //private CouserType couserType;

    @ManyToOne
    private Department department;

    public Section(Map<Integer, Student> students, Department department) {
        this.students = students;
        this.department = department;
    }
    public Section(Department department) {
        this.students = new HashMap<>();
        this.department = department;
    }
}
