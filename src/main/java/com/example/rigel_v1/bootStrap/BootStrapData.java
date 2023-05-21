package com.example.rigel_v1.bootStrap;
import com.example.rigel_v1.domain.*;
import com.example.rigel_v1.domain.enums.*;
import com.example.rigel_v1.repositories.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final ReportRepository reportRepository;
    private final EvaluationFormRepository evaluationFormRepository;
    private final QuestionRepository questionRepository;

    public BootStrapData(QuestionRepository questionRepository, DepartmentRepository departmentRepository, UserRepository userRepository, CourseRepository courseRepository, InstructorRepository instructorRepository, ReportRepository reportRepository, EvaluationFormRepository evaluationFormRepository) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.reportRepository = reportRepository;
        this.evaluationFormRepository = evaluationFormRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Department CS = new Department("CS");
        Department IE = new Department("IE");
        departmentRepository.save(CS);
        departmentRepository.save(IE);

        Student A = new Student("A", "a@gmail.com", "1234", true, null);
        CS.addStudent(A, 299);

        userRepository.save(A);
        departmentRepository.save(CS);

        Student B = new Student("B", "b@gmail.com", "1235", false, null);
        IE.addStudent(B, 399);

        userRepository.save(B);
        departmentRepository.save(IE);

        Student C = new Student("C", "c@gmail.com", "1236", true, null);
        IE.addStudent(C, 399);

        userRepository.save(C);
        departmentRepository.save(IE);

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

        StudentCourse cs299 = new StudentCourse(A, CourseName.CS299, instructor1);
        StudentCourse ie299 = new StudentCourse(B, CourseName.IE299);
        ie299.setInstructor(instructor2);
        StudentCourse cs399 = new StudentCourse(C, CourseName.CS399, instructor1);

        courseRepository.save(cs299);
        courseRepository.save(ie299);
        courseRepository.save(cs399);

        GradeForm C299_gradeForm = new GradeForm(false, CourseName.CS299, cs299, ReportStatus.changable);
        CriteriaReport CS299_criteriaReport = new CriteriaReport(false, CourseName.CS299, cs299, ReportStatus.changable);
        EvaluationForm CS299_evaluationForm = new EvaluationForm(7, true, true, true, Recommendation.satisfactory,  "companyName", cs299);

        cs299.uploadGradeForm(C299_gradeForm);
        cs299.uploadCriteriaReport(CS299_criteriaReport);
        cs299.uploadEvaluationForm(CS299_evaluationForm);

        reportRepository.save(C299_gradeForm);
        reportRepository.save(CS299_criteriaReport);
        evaluationFormRepository.save(CS299_evaluationForm);
       
        GradeForm C399_gradeForm = new GradeForm(false, CourseName.CS399, cs399, ReportStatus.changable);
        CriteriaReport CS399_criteriaReport = new CriteriaReport(false, CourseName.CS399, cs399, ReportStatus.changable);
        EvaluationForm CS399_evaluationForm = new EvaluationForm(5, false, true, false, Recommendation.not_recommended,  "companyName", cs299);

        cs399.uploadGradeForm(C399_gradeForm);
        cs399.uploadCriteriaReport(CS399_criteriaReport);
        cs399.uploadEvaluationForm(CS399_evaluationForm);

        reportRepository.save(C399_gradeForm);
        reportRepository.save(CS399_criteriaReport);
        evaluationFormRepository.save(CS399_evaluationForm);

        GradeForm IE299_gradeForm = new GradeForm(false, CourseName.IE299, ie299, ReportStatus.changable);
        CriteriaReport IE299_criteriaReport = new CriteriaReport(false, CourseName.IE299, ie299, ReportStatus.changable);
        EvaluationForm IE299_evaluationForm = new EvaluationForm(2, false, false, false, Recommendation.satisfactory,  "companyName", cs299);

        ie299.uploadGradeForm(IE299_gradeForm);
        ie299.uploadCriteriaReport(IE299_criteriaReport);
        ie299.uploadEvaluationForm(IE299_evaluationForm); 

        reportRepository.save(IE299_gradeForm);
        reportRepository.save(IE299_criteriaReport);
        evaluationFormRepository.save(IE299_evaluationForm);

        A.enrollCourse(cs399);
        B.enrollCourse(ie299);
        A.enrollCourse(cs299);

        cs299.getGradeForm().setSatisfaction(true);

        reportRepository.save(C299_gradeForm);

        userRepository.save(A);
        userRepository.save(B);

        courseRepository.save(cs299);
        courseRepository.save(ie299);
        courseRepository.save(cs399);

        System.out.println(cs299.getGradeForm().getReportStatus());

        Question q1 = new Question("AAAAA?", "BBBB", -1);
        questionRepository.save(q1);
        Question q2 = new Question("CCCCC?", "DDDD", -1);
        questionRepository.save(q2);




    }
}