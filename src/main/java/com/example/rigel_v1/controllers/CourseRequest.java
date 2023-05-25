package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.enums.CourseName;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseRequest {
    private CourseName name;
    @JsonProperty("student_id")
    private Long student_id;

    public CourseName getName() {
        return name;
    }

    public void setName(CourseName name) {
        this.name = name;
    }

    public Long getStudentId() {
        return student_id;
    }

    public void setStudentId(Long studentId) {
        this.student_id = studentId;
    }
}
