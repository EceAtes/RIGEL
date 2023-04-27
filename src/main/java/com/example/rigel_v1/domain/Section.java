package com.example.rigel_v1.domain;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
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

    public Section() {
    }

    public Section(Map<Integer, Student> students, Department department) {
        this.students = students;
        this.department = department;
    }
    public Section(Department department) {
        this.students = new HashMap<>();
        this.department = department;
    }

    public Map<Integer, Student> getStudents() {
        return students;
    }

    public void setStudents(Map<Integer, Student> students) {
        this.students = students;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


}
