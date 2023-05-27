package com.example.rigel_v1.bootStrap;
import com.example.rigel_v1.domain.*;
import com.example.rigel_v1.domain.enums.*;
import com.example.rigel_v1.repositories.*;

import com.example.rigel_v1.service.UsersService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class BootStrapData implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final ReportRepository reportRepository;
    private final QuestionRepository questionRepository;
    private final CriteriaReportRepository criteriaReportRepository;
    private final UsersService usersService;

    public BootStrapData( UsersService usersService, QuestionRepository questionRepository, DepartmentRepository departmentRepository, UserRepository userRepository, CourseRepository courseRepository, InstructorRepository instructorRepository, CriteriaReportRepository criteriaReportRepository, ReportRepository reportRepository) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.criteriaReportRepository = criteriaReportRepository;
        this.questionRepository = questionRepository;
        this.usersService = usersService;
        this.reportRepository = reportRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Department CS = new Department("CS");
        Department IE = new Department("IE");
        departmentRepository.save(CS);
        departmentRepository.save(IE);

        Student A = new Student("A", "a@gmail.com", "1234", true, null, 123456);
        CS.addStudent(A, 299);

        userRepository.save(A);

        Student B = new Student("B", "b@gmail.com", "1235", false, null,123457);
        CS.addStudent(B, 299);

        userRepository.save(B);
        Optional<Users> optional = userRepository.findById(Long.valueOf(1));
        System.out.println(optional.isPresent());


        Student C = new Student("C", "c@gmail.com", "1236", true, null,123458);
        CS.addStudent(C, 399);

        userRepository.save(C);

        System.out.println("Started");


        System.out.println("No of users: " + userRepository.count());
        System.out.println("No of departments: " + departmentRepository.count());
        System.out.println("No of CS students: " + CS.getTotalStuNo());
        System.out.println("No of IE students: " + IE.getTotalStuNo());

        Instructor instructor1 = new Instructor("Eray Tuzun", "tuzun@gmail.com", "2345", false, CS );
        Instructor instructor2 = new Instructor("Bahar Yetis", "yetis@gmail.com", "ieieie", false, CS);

        instructorRepository.save(instructor1);
        instructorRepository.save(instructor2);

        System.out.println("No of users: " + userRepository.count());

        StudentCourse cs299 = new StudentCourse(A, CourseName.CS299);
        StudentCourse ie299 = new StudentCourse(B, CourseName.IE299);
        StudentCourse cs399 = new StudentCourse(C, CourseName.CS399);

        courseRepository.save(cs299);
        courseRepository.save(ie299);
        courseRepository.save(cs399);

        Secretary secretary = new Secretary("Begüm Hanim", "aaaaaa@gmail.com", "aaaaaaaa", false, CS);
        userRepository.save(secretary);
        secretary.automatch(usersService);
        System.out.println("AAAAAAAAAAAaA");
        Optional<Users> optional1 = userRepository.findById(Long.valueOf(4));
        Optional<Users> optional2 = userRepository.findById(Long.valueOf(5));
        if(optional1.isPresent()){
            instructor1 = (Instructor) optional1.get();
            instructor2 = (Instructor) optional2.get();
        }
        Optional<StudentCourse> optional3 = courseRepository.findById(Long.valueOf(1));
        Optional<StudentCourse> optional4 = courseRepository.findById(Long.valueOf(2));
        if(optional3.isPresent()){
            cs299 = (StudentCourse) optional3.get();
            ie299 = (StudentCourse) optional4.get();
        }
        System.out.println(cs299.getInstructor());

        secretary.rematchStudent(usersService, instructor2.getId(), cs299.getId());

        Optional<StudentCourse> optional5 = courseRepository.findById(Long.valueOf(1));
        Optional<Users> optional6 = userRepository.findById(Long.valueOf(4));
        Optional<Users> optional7 = userRepository.findById(Long.valueOf(5));
        if(optional3.isPresent()){
            cs299 = (StudentCourse) optional5.get();
            instructor1 = (Instructor) optional6.get();
            instructor2 = (Instructor) optional7.get();

        }

        System.out.println(cs299.getInstructor());
        secretary.addUser(usersService, "D", "UWU", "asdfghjkl", true, Role.STUDENT, 22001578, new CourseName[]{CourseName.CS299});
        secretary.addUser(usersService, "D", "UWU", "asdfghjkl", true, Role.INSTRUCTOR,  0, null);

        secretary.createStudentsFromFile(usersService);

        Admin admin = new Admin("AAAAAAAA", "BBBBBBB","CCCCCCCCc", false, null);
        this.userRepository.save(admin);

        System.out.println(cs299.getInstructor());



        System.out.println(A.getCourses());

        Question q1 = new Question("AAAAA?", "HHHH", -1);
        Question q2 = new Question("BBBBB?", "IIII", -1);
        Question q3 = new Question("CCCCC?", "JJJJJ", -1);
        questionRepository.save(q1);
        questionRepository.save(q2);
        questionRepository.save(q3);
        ArrayList<Question> qs = new ArrayList<>();
        qs.add(q1);
        qs.add(q2);
        qs.add(q3);

        //GradeForm C299_gradeForm = new GradeForm(false, CourseName.CS299, cs299, ReportStatus.changable);
        CriteriaReport CS299_criteriaReport = new CriteriaReport(qs);
        //EvaluationForm CS299_evaluationForm = new EvaluationForm(7, true, true, true, Recommendation.satisfactory,  "companyName", cs299);

        //cs299.uploadGradeForm(C299_gradeForm);
        cs299.uploadCriteriaReport(CS299_criteriaReport);
        //cs299.uploadEvaluationForm(CS299_evaluationForm);

        //reportRepository.save(C299_gradeForm);
        criteriaReportRepository.save(CS299_criteriaReport);
        //evaluationFormRepository.save(CS299_evaluationForm);

        /*//GradeForm C399_gradeForm = new GradeForm(false, CourseName.CS399, cs399, ReportStatus.changable);
        CriteriaReport CS399_criteriaReport = new CriteriaReport(false, CourseName.CS399, cs399);
       // EvaluationForm CS399_evaluationForm = new EvaluationForm(5, false, true, false, Recommendation.not_recommended,  "companyName", cs299);

        //cs399.uploadGradeForm(C399_gradeForm);
        cs399.uploadCriteriaReport(CS399_criteriaReport);
        //cs399.uploadEvaluationForm(CS399_evaluationForm);

        //reportRepository.save(C399_gradeForm);
        criteriaReportRepository.save(CS399_criteriaReport);
        //evaluationFormRepository.save(CS399_evaluationForm);

        //GradeForm IE299_gradeForm = new GradeForm(false, CourseName.IE299, ie299, ReportStatus.changable);
        CriteriaReport IE299_criteriaReport = new CriteriaReport(false, CourseName.IE299, ie299, ReportStatus.changable);
       // EvaluationForm IE299_evaluationForm = new EvaluationForm(2, false, false, false, Recommendation.satisfactory,  "companyName", cs299);

        //ie299.uploadGradeForm(IE299_gradeForm);
        ie299.uploadCriteriaReport(IE299_criteriaReport);
        //ie299.uploadEvaluationForm(IE299_evaluationForm);

        //reportRepository.save(IE299_gradeForm);
        criteriaReportRepository.save(IE299_criteriaReport);
        //evaluationFormRepository.save(IE299_evaluationForm);

        A.enrollCourse(cs399);
        B.enrollCourse(ie299);
        A.enrollCourse(cs299);
*/
        //cs299.getGradeForm().setSatisfaction(true);

        //reportRepository.save(C299_gradeForm);

   /*     userRepository.save(A);
        userRepository.save(B);

        courseRepository.save(cs299);
        courseRepository.save(ie299);
        courseRepository.save(cs399);

    */    //System.out.println(cs299.getGradeForm().getReportStatus());

        /*
        IE299_criteriaReport.addQuestion(q1);
        IE299_criteriaReport.addQuestion(q2);
        IE299_criteriaReport.addQuestion(q3);
        IE299_criteriaReport.addQuestion(q4);
        IE299_criteriaReport.addQuestion(q5);
        IE299_criteriaReport.addQuestion(q6);
        IE299_criteriaReport.addQuestion(q7);*/
        //criteriaReportRepository.save(IE299_criteriaReport);
/*
        Admin admin = new Admin("admin", "mail", "pass", false, CS);
        userRepository.save(admin);*/


    }
}