package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.*;
import com.example.rigel_v1.repositories.FeedbackRepository;
import com.example.rigel_v1.repositories.InternshipReportRepository;
import com.example.rigel_v1.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/feedbacks")
@RestController
@CrossOrigin("http://localhost:3000")
public class FeedbackController {
        private final FeedbackRepository feedbackRepository;
        private final UserRepository userRepository;
        private final InternshipReportRepository internshipReportRepository;

    public FeedbackController(FeedbackRepository feedbackRepository, UserRepository userRepository, InternshipReportRepository internshipReportRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.internshipReportRepository = internshipReportRepository;
    }

    @GetMapping("/{id}")
    public Optional<Feedback> getUser(@PathVariable Long id){
        return feedbackRepository.findById(id);
    }

    @PostMapping
    public void addFeedback(@NonNull @RequestBody FeedbackCreationRequest req){
        Optional<Users> optional1 = userRepository.findById(Long.valueOf(req.getFeedback_giver_id()));
        Optional<InternshipReport> optional2 = internshipReportRepository.findById(Long.valueOf(req.getInternship_report_id()));
        System.out.println(optional2.toString());
        if (optional1.isPresent() && optional2.isPresent()){
            Users user = optional1.get();
            InternshipReport report = optional2.get();
            Feedback feedback = new Feedback(req.getFeedback(), (FeedbackUser) user);
            feedbackRepository.save(feedback);
            if(user instanceof TA){
                report.getTA_Feedback().add(feedback);
                internshipReportRepository.save(report);
            }
            else if(user instanceof Instructor){
                report.getInstructorFeedback().add(feedback);
                internshipReportRepository.save(report);
            }
        }
    }
}

@Getter
@Setter
@NoArgsConstructor
class FeedbackCreationRequest{
    @JsonProperty("feedback")
    private String feedback;
    @JsonProperty("feedback_giver_id")
    private Long feedback_giver_id;
    @JsonProperty("internship_report_id")
    private Long internship_report_id;
}