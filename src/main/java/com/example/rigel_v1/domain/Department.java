package com.example.rigel_v1.domain;

import com.example.rigel_v1.NullKeySerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import java.util.*;

//@Document("Administrations")
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //private Statistics statistics;
    @JsonProperty("totalStuNo")
    private int totalStuNo;

    @JsonProperty("internedStuNo")
    private int internedStuNo;

    @OneToMany
    @JoinColumn(name = "department_id")
    @JsonSerialize(using = NullKeySerializer.class)
    private Map<String, Administration> administrators = new HashMap<>();//maybe add id for them too?

    @JsonProperty("name")
    private String name;

    @OneToMany
    @JoinColumn(name = "department_id")
    @JsonSerialize(using = NullKeySerializer.class)
    private Map<String, Instructor> instructors = new HashMap<>();//maybe add id for them too?

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
        administrators = new HashMap<>();
        instructors = new HashMap<>();
        students_299 = new HashMap<>();
        students_399 = new HashMap<>();
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

    public Map<Long, Student> getStudents_299() {
        return students_299;
    }

    public void setStudents_299(Map<Long, Student> students_299) {
        this.students_299 = students_299;
    }

    public Map<Long, Student> getStudents_399() {
        return students_399;
    }

    public void setStudents_399(Map<Long, Student> students_399) {
        this.students_399 = students_399;
    }

    public List<Department> getSections() {
        return sections;
    }

    public void setSections(List<Department> sections) {
        this.sections = sections;
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

    public Map<String, Administration> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(Map<String, Administration> administrators) {
        this.administrators = administrators;
    }

    public Map<String, Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(Map<String, Instructor> instructors) {
        this.instructors = instructors;
    }

    public void addInstructor(Instructor instructor){
        this.instructors.put(instructor.getName(), instructor);
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
