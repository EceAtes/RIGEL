package com.example.rigel_v1.service;

import com.example.rigel_v1.domain.Instructor;
import com.example.rigel_v1.domain.Student;
import com.example.rigel_v1.domain.StudentCourse;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PDFService {
    public byte[] export() throws IOException, DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
    
        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
    
        Paragraph paragraph = new Paragraph("RIGEL", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
    
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);
    
        Paragraph paragraph2 = new Paragraph("PDF Generated UwU.", fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
    
        document.add(paragraph);
        document.add(paragraph2);
        document.close();
    
        return outputStream.toByteArray();
    }

    public byte[] exportSign(Instructor instructor) throws IOException, DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
    
        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
    
        Paragraph paragraph = new Paragraph("RIGEL", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
    
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);
    
        Paragraph paragraph2 = new Paragraph("PDF Generated UwU.", fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
    
        document.add(paragraph);
        document.add(paragraph2);
        document.close();
    
        return outputStream.toByteArray();
    }


    /*
    public void export(HttpServletResponse response) throws IOException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph("RIGEL", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Paragraph paragraph2 = new Paragraph("PDF Generated UwU.", fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(paragraph);
        document.add(paragraph2);
        document.close();

    }*/
}
