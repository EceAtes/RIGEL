package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.CriteriaReport;
import com.example.rigel_v1.domain.GradeForm;
import com.example.rigel_v1.repositories.GradeFormRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    public GradeForm updateGradeForm(@PathVariable Long id, @RequestBody FormUpdate req){
        Optional<GradeForm> optional = gradeFormRepository.findById(id);
        if(optional.isPresent()){
            GradeForm form = optional.get();
            if(req.getGeneratedFormKey() != null && req.getGeneratedFormKey() != ""){
                form.setGeneratedFormKey(req.getGeneratedFormKey());
            }
            form.setWillBeRevised(req.isWillBeRevised());
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

@Getter @Setter
@NoArgsConstructor
class FormUpdate{
    private String generatedFormKey;
    private boolean willBeRevised;
}