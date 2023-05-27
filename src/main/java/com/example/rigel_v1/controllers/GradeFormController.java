package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.CriteriaReport;
import com.example.rigel_v1.domain.GradeForm;
import com.example.rigel_v1.repositories.GradeFormRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/grade_form")
@RestController
public class GradeFormController {
    GradeFormRepository gradeFormRepository;

    public GradeFormController(GradeFormRepository gradeFormRepository) {
        this.gradeFormRepository = gradeFormRepository;
    }

    @PostMapping
    public GradeForm createGradeForm(){
        GradeForm report = new GradeForm();
        gradeFormRepository.save(report);
        return report;
    }

    ///patch/get yaz>!!!!!!!!!!!!!!

}
