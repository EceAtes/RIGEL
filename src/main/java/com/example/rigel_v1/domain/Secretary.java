package com.example.rigel_v1.domain;

import com.example.rigel_v1.controllers.UsersController;
import com.example.rigel_v1.domain.enums.CourseName;
import com.example.rigel_v1.domain.enums.Role;
import com.example.rigel_v1.service.UsersService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//@Document("Administrations")
@Component
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Secretary extends Users{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    @JoinColumn(name = "secretary_id")
    private Map<Integer, GradeForm> gradeForms;

    private Date addDropDeadline;
    //private Date semesterEnd;?????

    public Secretary(String name, String email, String password, boolean notificationToMail, Department department) {
        super(name, email, password, notificationToMail, Role.SECRETARY, department);
        this.gradeForms = new HashMap<>();
    }


    /*public Secretary(Map<Integer, CriteriaReport> criteriaReports, File eSignature, Map<Integer, EvaluationForm> EvaluationForm, Map<Integer, GradeForm> gradeForms) {//, Statistics statistics
        super(criteriaReports, eSignature);//, statistics
        this.EvaluationForm = EvaluationForm;
        this.gradeForms = gradeForms;
    }*/

    /*ublic Secretary(String name, String email, String password, boolean notificationToMail, Department department, Map<Integer, CriteriaReport> criteriaReports, File eSignature, Map<Integer, EvaluationForm> EvaluationForm, Map<Integer, GradeForm> gradeForms) {//, Statistics statistics
        super(name, email, password, notificationToMail, Role.SECRETARY, department, criteriaReports, eSignature);//, statistics
        this.gradeForms = gradeForms;
    }*/

    /*public Secretary(String name, String email, String password, boolean notificationToMail, Department department, Set<Notification> notification, Map<Integer, CriteriaReport> criteriaReports, File eSignature, Map<Integer, EvaluationForm> EvaluationForm, Map<Integer, GradeForm> gradeForms) {//, Statistics statistics
        super(name, email, password, notificationToMail, Role.SECRETARY, department, notification, criteriaReports, eSignature);//, statistics
        this.EvaluationForm = EvaluationForm;
        this.gradeForms = gradeForms;
    }*/

    public void addUser(UsersService usersService, String name, String email, String password, boolean notifToMail, Role role, Department department, int studentId, CourseName[] courseTypes){
        Users newUser = usersService.createUser(name, email, password, notifToMail, role, department, studentId);
        if(role == Role.STUDENT){   ///equals()??
            for(int i = 0; i < courseTypes.length; i++){
                StudentCourse studentCourse = new StudentCourse((Student) newUser, courseTypes[i]);
                ((Student) newUser).getCourses().add(studentCourse);
            }
        }
    }

    public void automatch(UsersService usersService){
        HashMap<Long, Student> allStudents = new HashMap<>();
        allStudents.putAll(this.getDepartment().getStudents_299());
        allStudents.putAll(this.getDepartment().getStudents_399());
        usersService.automatch(this.getDepartment());
        boolean isAllMatched = true;
    }

    public void createStudentFolders(){
    //    if(semester)

    }

    public boolean addDropPeriodPassed(Date today){
        if(today.compareTo(addDropDeadline) == -1){ //???
            return true;
        }
        return false;
    }

}