package com.example.rigel_v1.controllers;

import com.example.rigel_v1.service.*;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@CrossOrigin("http://localhost:3000")
public class PDFController {
    private final PDFService pdfService;
    private final GoogleDriveService googleDriveService;

    public PDFController(PDFService pdfService, GoogleDriveService googleDriveService) {
        this.pdfService = pdfService;
        this.googleDriveService = googleDriveService;
    }

    @PostMapping("/criteria-report")
    public void submitCriteriaReport(@RequestParam("folderKey") String folderKey, @RequestParam("userId") Long userId) throws IOException, DocumentException {
            //studentcourse varsa
            //submitleyip confirmledise
            System.out.println(googleDriveService.uploadGeneratedFile(pdfService.export(), folderKey));
            System.out.println("SUCCESS");

    }

    /*
    //downloads the generated pdf file
    @GetMapping("/generate/pdf")
    public void generatePDF(HttpServletResponse response) throws IOException, DocumentException {
        File pdfFile = pdfService.export();

        // Set the content type and attachment header
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=generatedPDF.pdf");

        // Copy the file to the response output stream
        try (InputStream inputStream = new FileInputStream(pdfFile);
             OutputStream outputStream = response.getOutputStream()) {
            IOUtils.copy(inputStream, outputStream);
        }
    }*/

    /*@GetMapping("/pdf/generate")
    public void generatePDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.pdfService.export(response);
    }*/
}
