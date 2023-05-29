package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.StudentCourse;
import com.example.rigel_v1.domain.enums.Recommendation;
import com.example.rigel_v1.repositories.CourseRepository;
import com.example.rigel_v1.service.*;
import com.lowagie.text.DocumentException;

import jakarta.persistence.Entity;
import lombok.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;

@Controller
@CrossOrigin("http://localhost:3000")
public class PDFController {
    private final PDFService pdfService;
    private final GoogleDriveService googleDriveService;
    private final CourseRepository courseRepository;

    public PDFController(PDFService pdfService, GoogleDriveService googleDriveService,
            CourseRepository courseRepository) {
        this.pdfService = pdfService;
        this.googleDriveService = googleDriveService;
        this.courseRepository = courseRepository;
    }

    @PostMapping("/grade-form")
    public void generateSummerTrainingGradeForm(@RequestBody GradeFormRequestObject requestObject)
            throws IOException, DocumentException {
        Long courseId = requestObject.getCourseId();
        boolean isRelated = requestObject.isRelated();
        boolean isSupervisorEngineer = requestObject.isSupervisorEngineer();
        boolean revisionRequired = requestObject.isRevisionRequired();
        String recommendation = requestObject.getRecommendation();
        StudentCourse studentCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Student course not found"));
        if (recommendation.equals(Recommendation.recommended.toString())) {
            studentCourse.getCriteriaReport().setRecommendation(Recommendation.recommended);
        }
        else if (recommendation.equals(Recommendation.satisfactory.toString())) {
            studentCourse.getCriteriaReport().setRecommendation(Recommendation.satisfactory);
        }
        else {
            studentCourse.getCriteriaReport().setRecommendation(Recommendation.not_recommended);
        }
        String fileKey = googleDriveService.uploadGeneratedFile(pdfService.markPDF(studentCourse), studentCourse);
        System.out.println("SUCCESS");
        studentCourse.getLastGradeReport().setGeneratedFormKey(fileKey);
    }
}

@Entity
@Setter @Getter @NoArgsConstructor
class GradeFormRequestObject {
    private Long courseId;
    private boolean isRelated;
    private boolean isSupervisorEngineer;
    private boolean revisionRequired;
    private String recommendation;

    public GradeFormRequestObject(Long courseId, boolean isRelated, boolean isSupervisorEngineer, boolean revisionRequired, String recommendation) {
        this.courseId = courseId;
        this.isRelated = isRelated;
        this.isSupervisorEngineer = isSupervisorEngineer;
        this.revisionRequired = revisionRequired;
        this.recommendation = recommendation;
    }

}