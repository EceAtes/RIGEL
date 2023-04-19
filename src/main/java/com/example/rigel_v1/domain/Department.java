package com.example.rigel_v1.domain;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //private Statistics statistics;
    private int totalStuNo;
    private int internedStuNo;
    //private Administration administration
    private String name;
    //private Map<Integer, Instructor> instructors;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<Long, Student> students;
    //private Secretary secretary;

    public Department(int totalStuNo, int internedStuNo, String name) {
        this.totalStuNo = totalStuNo;
        this.internedStuNo = internedStuNo;
        this.name = name;
        this.students = new HashMap<>();
    }

    public Department(int totalStuNo, int internedStuNo, String name, Map<Long, Student> students) {
        this.totalStuNo = totalStuNo;
        this.internedStuNo = internedStuNo;
        this.name = name;
        this.students = students;
    }

    public Department() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalStuNo() {
        totalStuNo = students.size();
        return totalStuNo;
    }

    public void setTotalStuNo(int totalStuNo) {
        this.totalStuNo = totalStuNo;
    }

    public int getInternedStuNo() {
        return internedStuNo;
    }

    public void setInternedStuNo(int internedStuNo) {
        this.internedStuNo = internedStuNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Long, Student> getStudents() {
        return students;
    }

    public void setStudents(Map<Long, Student> students) {
        this.students = students;
    }
}
