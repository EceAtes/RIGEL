package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.CriteriaReport;
import com.example.rigel_v1.domain.Users;
import com.example.rigel_v1.repositories.CriteriaReportRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
