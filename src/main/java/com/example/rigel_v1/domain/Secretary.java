package com.example.rigel_v1.domain;

import com.example.rigel_v1.domain.enums.CourseName;
import com.example.rigel_v1.domain.enums.Role;
import com.example.rigel_v1.service.UsersService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;
import java.util.*;


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

    private String addDropDeadline;            //yyyy-mm-dd
    private String withdrawDeadline;           //yyyy-mm-dd
    private String departmentFolderKey;

    private boolean isSemesterStarted;

    @ElementCollection
    private List<String> reportFolderKeys;    //[0] Internship Reports, [1] Summer Training Grade Forms

    public Secretary(String name, String email, String password, boolean notificationToMail, Department department) {
        super(name, email, password, notificationToMail, Role.SECRETARY, department);
        this.gradeForms = new HashMap<>();
        reportFolderKeys = new ArrayList<String>();
        isSemesterStarted = false;
    }

    public void addUser(UsersService usersService, String name, String email, String password, boolean notifToMail, Role role, int studentId, CourseName[] courseTypes){
        System.out.println("ENTERED USER");
        Users newUser;
        if(courseTypes != null){
            newUser = usersService.createUser(name, email, password, notifToMail, role, this.getDepartment(), studentId, courseTypes);
        } else {
            newUser = usersService.createUser(name, email, password, notifToMail, role, this.getDepartment(), studentId, null);
        }

    }

    public void automatch(UsersService usersService){
        HashMap<Long, Student> allStudents = new HashMap<>();
        allStudents.putAll(this.getDepartment().getStudents_299());
        allStudents.putAll(this.getDepartment().getStudents_399());
        usersService.automatch(this.getDepartment());
    }

    public void rematchStudent(UsersService usersService, Long instructorId, Long courseId){
        usersService.rematchStudent(instructorId, courseId);
    }

    //hardcoded path
    public void createStudentsFromFile(UsersService usersService){
        System.out.println("ENTERED SECRETARY");
        String basePath = System.getProperty("user.dir");
        String filePath = basePath + "\\users.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Scanner scanner = new Scanner(line);
                scanner.useDelimiter(",(?![^\\[]*\\])"); // Delimiter to split the line

                int studentId = 0;
                CourseName[] courseNames = new CourseName[2];

                String name = scanner.next().trim();
                String email = scanner.next().trim();
                String password = scanner.next().trim();
                boolean notifToMail = Boolean.parseBoolean(scanner.next().trim());
                Role role = Role.valueOf(scanner.next().trim());
                if(scanner.hasNext()){
                    studentId = Integer.parseInt(scanner.next().trim());
                }
                if(scanner.hasNext()){
                    String arrayString = scanner.next();
                    String[] array = arrayString.replaceAll("\\[|\\]", "").split(",");
                    for(int i = 0; i < array.length; i++){
                        array[i] = array[i].trim();
                        CourseName courseName = CourseName.valueOf(array[i]);
                        courseNames[i] = courseName;
                    }
                }

                scanner.close();
                addUser(usersService, name, email, password, notifToMail, role, studentId, courseNames);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFolderKeys(String folderKey){
        reportFolderKeys.add(folderKey);
    }

    /*
     *  To start the courses, the secretary waits add drop time to be finished
     */
    public boolean addDropPeriodPassed() {
        LocalDate currentDate = LocalDate.now();
        LocalDate inputDate = LocalDate.parse(addDropDeadline);
        System.out.println("Input date: " + inputDate.toString());
        System.out.println("Current date: " + currentDate.toString());
        int comparison = inputDate.compareTo(currentDate);

        // true, if the add-drop period has passed
        return comparison < 0;
    }

    /*
     *  Department secretary manually enters the student score that is given the company 
     */
    public void enterCompanyGrade(StudentCourse studentCourse, int grade){
        studentCourse.setCompanyScore(grade);
        //studentCourse.setStatus(Status.); //???????????????
    }

    
    /*
     *  Department secretary manually enters the company name 
     */
    public void enterCompanyName(StudentCourse studentCourse, String name){
        studentCourse.setCompanyName(name);
    }

}


