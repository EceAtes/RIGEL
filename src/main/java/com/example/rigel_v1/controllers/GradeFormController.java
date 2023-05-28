package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.CriteriaReport;
import com.example.rigel_v1.domain.GradeForm;
import com.example.rigel_v1.repositories.GradeFormRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PatchMapping("/{id}")
    public GradeForm updateGradeForm(@PathVariable Long id, @RequestParam String generatedFormKey, @RequestParam boolean wilBbeRevised){
        Optional<GradeForm> optional = gradeFormRepository.findById(id);
        if(optional.isPresent()){
            GradeForm form = optional.get();
            if(generatedFormKey != null && generatedFormKey != ""){
                form.setGeneratedFormKey(generatedFormKey);
            }
            form.setWillBeRevised(wilBbeRevised);
            return form;
        }
        return null;
    }

    @GetMapping("/{id}")
    public GradeForm getGradeForm(@PathVariable Long id){
        Optional<GradeForm> optional = gradeFormRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }


}


//course içine internship report id koy