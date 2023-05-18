package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.CriteriaReport;
import com.example.rigel_v1.domain.Report;
import com.example.rigel_v1.domain.Student;
import com.example.rigel_v1.domain.StudentCourse;
import com.example.rigel_v1.domain.Users;
import com.example.rigel_v1.repositories.ReportRepository;
import com.example.rigel_v1.repositories.UserRepository;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/criteria")
@RestController
public class CriteriaReportController {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public CriteriaReportController(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    /*@PutMapping
    public void saveReport(@RequestBody CriteriaReport criteria){
        System.out.println("saving report...");
        Optional<Report> optionalReport  =  reportRepository.findById(Long.valueOf(criteria.getId()));
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            if (report instanceof CriteriaReport) {
                CriteriaReport criteriaReport = (CriteriaReport) report;
                StudentCourse course = new StudentCourse();
                ...
            }
        }
    }*/

    @PostMapping
    public void submitReport(@RequestBody CriteriaReport criteria){
        System.out.println("saving report...");
        Optional<Report> optionalReport  =  reportRepository.findById(Long.valueOf(criteria.getId()));
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            if (report instanceof CriteriaReport) {
                CriteriaReport criteriaReport = (CriteriaReport) report;
                //...
            }
        }
        else{
            System.out.println("sth went wrong -- report is not present");
        }

    }
}

