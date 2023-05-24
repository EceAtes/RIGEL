package com.example.rigel_v1.domain;

import com.example.rigel_v1.NullKeySerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

//@Document("Administrations")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //private Statistics statistics;
    @JsonProperty("totalStuNo")
    private int totalStuNo;

    @JsonProperty("internedStuNo")
    private int internedStuNo;

    @JsonProperty("name")
    private String name;

    @OneToMany
    @JoinColumn(name = "department_id")
    @JsonSerialize(using = NullKeySerializer.class)
    private Map<Long, Instructor> instructors = new HashMap<>();//maybe add id for them too?

    @OneToMany
    @JoinColumn(name = "department_id") //id of the "one"
    @JsonSerialize(using = NullKeySerializer.class)
    private Map<Long, Student> students_299 = new HashMap<>();

    @OneToMany
    @JoinColumn(name = "department_id") //id of the "one"
    @JsonSerialize(using = NullKeySerializer.class)
    private Map<Long, Student> students_399 = new HashMap<>();

    @OneToMany
    @JoinColumn(name = "department_id") //id of the "one"
    private List<Department> sections = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "secretary_id", referencedColumnName = "id")
    private List<Secretary> secretaries = new ArrayList<>();


    public Department(int totalStuNo, int internedStuNo, String name, Map<Long, Student> students_299, Map<Long, Student> students_399, List<Department> sections) {
        this.totalStuNo = totalStuNo;
        this.internedStuNo = internedStuNo;
        this.name = name;
        this.students_299 = students_299;
        this.students_399 = students_399;
        this.sections = sections;
    }

    public Department(String name) {
        this.name = name;
        this.totalStuNo = 0;
        this.internedStuNo = 0;
    }

    public Department(int totalStuNo, int internedStuNo,String name) {
        this.name = name;
        this.totalStuNo = totalStuNo;
        this.internedStuNo = internedStuNo;
        instructors = new HashMap<>();
        students_299 = new HashMap<>();
        students_399 = new HashMap<>();
    }

    public void addStudent(Student student, int courseType){
        totalStuNo++;
        if(courseType == 299){
            this.students_299.put(student.getId(), student);
        } else{
            this.students_399.put(student.getId(), student);
        }
        student.setDepartment(this);
    }

    public void addInstructor(Instructor instructor){
        this.instructors.put(instructor.getId(), instructor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", totalStuNo=" + totalStuNo +
                ", internedStuNo=" + internedStuNo +
                ", name='" + name + '\'' +
                '}';
    }
/*public void initializeCourse(Course course){
    }

    public void endCourse(Course course){
    }
     */
}
