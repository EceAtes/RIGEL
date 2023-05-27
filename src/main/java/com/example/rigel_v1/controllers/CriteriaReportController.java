package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.CriteriaReport;
import com.example.rigel_v1.domain.Question;
import com.example.rigel_v1.domain.StudentCourse;
import com.example.rigel_v1.domain.Users;
import com.example.rigel_v1.domain.enums.ReportStatus;
import com.example.rigel_v1.repositories.CourseRepository;
import com.example.rigel_v1.repositories.CriteriaReportRepository;
import com.example.rigel_v1.repositories.QuestionRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RequestMapping("/criteria_report")
@RestController
public class CriteriaReportController {
    private CriteriaReportRepository criteriaReportRepository;
    private CourseRepository courseRepository;
    private QuestionRepository questionRepository;

    public CriteriaReportController(CriteriaReportRepository criteriaReportRepository, CourseRepository courseRepository, QuestionRepository questionRepository) {
        this.criteriaReportRepository = criteriaReportRepository;
        this.courseRepository = courseRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/{id}")
    public Optional<CriteriaReport> getCriteriaReport(@PathVariable Long id){
        return criteriaReportRepository.findById(id);
    }

    @PatchMapping("/edit/{id}")
    public Optional<CriteriaReport> editCriteriaReport(@PathVariable Long id, @RequestParam("status") ReportStatus status){
        Optional<CriteriaReport> optional = criteriaReportRepository.findById(id);
        if(optional.isPresent()){
            CriteriaReport report = optional.get();
            report.setReportStatus(status);
            criteriaReportRepository.save(report);
            System.out.println(report.getReportStatus());

        }
        return null;
    }

    @PatchMapping("/answer/{id}/{qID}")
    public void answerQuestion(@PathVariable Long id, @PathVariable int qID, @RequestBody QuestionUpdate update){
        System.out.println("ENTERED");
        Optional<CriteriaReport> optional = criteriaReportRepository.findById(id);
        if(optional.isPresent()){
            CriteriaReport report = optional.get();
            Question currQ = report.getQuestions().get(qID);
            if(update.getAnswer() != null){
                currQ.setAnswer(update.getAnswer());
                questionRepository.save(currQ);
            }
            if(update.getScore() != -1){
                currQ.setScore(update.getScore());
                questionRepository.save(currQ);
            }
            criteriaReportRepository.save(report);
        }
    }


    @PostMapping()
    public CriteriaReport createCriteriaReport(){
            Question q1 = new Question("AAAAA?", "HHHH", -1);
            Question q2 = new Question("BBBBB?", "IIII", -1);
            Question q3 = new Question("CCCCC?", "JJJJJ", -1);
            /*Question q4 = new Question("DDDDD?", "KKKKK", -1);
            Question q5 = new Question("EEEEE?", "LLLLL", -1);
            Question q6 = new Question("FFFFFF?", "MMMM", -1);
            Question q7 = new Question("GGGGGG?", "NNNN", -1);*/
            questionRepository.save(q1);
            questionRepository.save(q2);
            questionRepository.save(q3);
            /*questionRepository.save(q4);
            questionRepository.save(q5);
            questionRepository.save(q6);
            questionRepository.save(q7);*/
            ArrayList<Question> qs =  new ArrayList<>();
            qs.add(q1);
            qs.add(q2);
            qs.add(q3);
            CriteriaReport criteriaReport = new CriteriaReport(qs);
            criteriaReportRepository.save(criteriaReport);
            return criteriaReport;

    }

}


@Getter
@Setter
@NoArgsConstructor
class QuestionUpdate{
    @JsonProperty("answer")
    private String answer;
    @JsonProperty("score")
    private int score;

}

