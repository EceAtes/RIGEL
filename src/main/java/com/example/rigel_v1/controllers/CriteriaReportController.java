package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.CriteriaReport;
import com.example.rigel_v1.domain.Users;
import com.example.rigel_v1.domain.enums.ReportStatus;
import com.example.rigel_v1.repositories.CriteriaReportRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/criteria_report")
@RestController
public class CriteriaReportController {
    private CriteriaReportRepository criteriaReportRepository;

    public CriteriaReportController(CriteriaReportRepository criteriaReportRepository) {
        this.criteriaReportRepository = criteriaReportRepository;
    }



    @GetMapping("/{id}")
    public Optional<CriteriaReport> getCriteriaReport(@PathVariable Long id){
        return criteriaReportRepository.findById(id);
    }

    @PatchMapping("/edit/{id}")
    public Optional<CriteriaReport> editCriteriaReport(@PathVariable Long id, ReportStatus status){
        Optional<CriteriaReport> optional = criteriaReportRepository.findById(id);
        if(optional.isPresent()){
            CriteriaReport report = optional.get();
            report.setReportStatus(status);
            criteriaReportRepository.save(report);
            System.out.println(report.getReportStatus());
        }
        return null;
    }

}
