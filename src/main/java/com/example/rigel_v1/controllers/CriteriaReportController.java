package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.CriteriaReport;
import com.example.rigel_v1.domain.Question;
import com.example.rigel_v1.domain.StudentCourse;
import com.example.rigel_v1.domain.Users;
import com.example.rigel_v1.domain.enums.ReportStatus;
import com.example.rigel_v1.domain.enums.Status;
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
    public Optional<CriteriaReport> editCriteriaReport(@PathVariable Long id, @RequestBody StatusUpdate req){
        Optional<CriteriaReport> optional = criteriaReportRepository.findById(id);
        if(optional.isPresent()){
            CriteriaReport report = optional.get();
            report.setReportStatus(req.getStatus());
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
            Question q1 = new Question("1) Able to perform work at the level\n" +
                    "expected from a summer training in\n" +
                    "the area of computer engineering.\n" +
                    "(this is the evaluation of all the work\n" +
                    "done in the summer training)", "", -1);
            Question q2 = new Question("(2) Solves complex engineering\n" +
                    "problems by applying principles of\n" +
                    "engineering, science, and\n" +
                    "mathematics.", "", -1);
            Question q3 = new Question("(3) Recognizes ethical and\n" +
                    "professional responsibilities in\n" +
                    "engineering situations", "", -1);
            Question q4 = new Question("(4) Able to make informed\n" +
                    "judgments that consider the impact\n" +
                    "of engineering solutions in global,\n" +
                    "economic, environmental, and\n" +
                    "societal contexts.", "", -1);
            Question q5 = new Question("(5) Able to acquire new knowledge\n" +
                    "using appropriate learning strategy\n" +
                    "or strategies.", "", -1);
            Question q6 = new Question("(6) Able to apply new knowledge as\n" +
                    "needed.", "", -1);
            Question q7 = new Question("(7) Has awareness about diversity,\n" +
                    "equity, and inclusion.", "", -1);
            Question q8 = new Question("Able to prepare reports with high\n" +
                    "standards in terms of content,\n" +
                    "organization, style and language\n" +
                    "(the Summer Training report itself is\n" +
                    "to be evaluated)", "", -1);
            questionRepository.save(q1);
            questionRepository.save(q2);
            questionRepository.save(q3);
            questionRepository.save(q4);
            questionRepository.save(q5);
            questionRepository.save(q6);
            questionRepository.save(q7);
            questionRepository.save(q8);
            ArrayList<Question> qs =  new ArrayList<>();
            qs.add(q1);
            qs.add(q2);
            qs.add(q3);
            qs.add(q4);
            qs.add(q5);
            qs.add(q6);
            qs.add(q7);
            qs.add(q8);
            CriteriaReport criteriaReport = new CriteriaReport(qs);
            criteriaReportRepository.save(criteriaReport);

            return criteriaReport;

        }



}

@Getter
@Setter
@NoArgsConstructor
class StatusUpdate{
    @JsonProperty("status")
    private ReportStatus status;
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

