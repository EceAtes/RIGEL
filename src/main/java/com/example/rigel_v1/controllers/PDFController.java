package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.StudentCourse;
import com.example.rigel_v1.domain.enums.Recommendation;
import com.example.rigel_v1.repositories.CourseRepository;
import com.example.rigel_v1.service.*;
import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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
    public void generateSummerTrainingGradeForm(@RequestParam Long courseId, @RequestParam String recommendation)
            throws IOException, DocumentException {
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