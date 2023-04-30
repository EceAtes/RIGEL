package com.example.rigel_v1.domain;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //private Statistics statistics;
    private int totalStuNo;
    private int internedStuNo;
    //private Administration departmentHead;
    private String name;
    //private Map<Integer, Instructor> instructors;

    @OneToMany
    @JoinColumn(name = "department_id") //id of the "one"
    private Map<Long, Student> students_299;

    @OneToMany
    @JoinColumn(name = "department_id") //id of the "one"
    private Map<Long, Student> students_399;

    @OneToMany
    @JoinColumn(name = "department_id") //id of the "one"
    private List<Department> sections;
    //private Secretary secretary;


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
        this.sections = new ArrayList<>();
        this.students_299 = new HashMap<>();
        this.students_399 = new HashMap<>();
        this.totalStuNo = 0;
        this.internedStuNo = 0;
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
                ", students_299=" + students_299 +
                ", students_399=" + students_399 +
                ", sections=" + sections +
                '}';
    }

    /*public void initializeCourse(Course course){
    }

    public void endCourse(Course course){
    }
     */
}
