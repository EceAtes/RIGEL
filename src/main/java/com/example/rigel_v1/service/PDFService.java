package com.example.rigel_v1.service;

import com.example.rigel_v1.domain.StudentCourse;
import com.example.rigel_v1.domain.enums.CourseName;
import com.example.rigel_v1.domain.enums.Recommendation;
import com.lowagie.text.*;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PDFService {

    public byte[] markPDF(StudentCourse studentCourse) throws IOException, DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        String existingPdfPath = "src/main/resources/GradeFormTemplate.pdf";

        PdfReader reader = new PdfReader(existingPdfPath);

        PdfStamper stamper = new PdfStamper(reader, outputStream);
        Font watermarkFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        PdfContentByte content = stamper.getUnderContent(1);

        String name = studentCourse.getCourseTaker().getName();
        Phrase watermark = new Phrase(name, watermarkFont);
        ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 2 / 5,
                PageSize.A4.getHeight() * 4 / 5, 0);

        String companyName = studentCourse.getCompanyName();
        watermark = new Phrase(companyName, watermarkFont);
        ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() / 2 + 10,
                PageSize.A4.getHeight() * 4 / 5 - 20, 0);

        watermark = new Phrase("X", watermarkFont);
        if (studentCourse.getCourseName().equals(CourseName.CS299)) {
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 2 / 5 - 5,
                    PageSize.A4.getHeight() * 4 / 5 - 43, 0);
        }
        else{
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() / 2 + 15,
                    PageSize.A4.getHeight() * 4 / 5 - 43, 0);
        }

        watermark = new Phrase(String.valueOf(studentCourse.getCompanyScore()), watermarkFont);
        ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5,
                PageSize.A4.getHeight() * 4 / 5 - 90, 0);
        if(studentCourse.isRelated()){
            watermark = new Phrase("Y", watermarkFont);
        }
        else{
            watermark = new Phrase("N", watermarkFont);
        }
        ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5,
                PageSize.A4.getHeight() * 4 / 5 - 120, 0);

        if(studentCourse.isSupervisorEngineer()){
            watermark = new Phrase("Y", watermarkFont);
        }
        else{
            watermark = new Phrase("N", watermarkFont);
        }
        ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5,
                PageSize.A4.getHeight() * 4 / 5 - 150, 0);

        watermark = new Phrase("X", watermarkFont);
        if(studentCourse.getLastGradeReport().isWillBeRevised()){
            // PART B
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() / 2 - 10,
                    PageSize.A4.getHeight() * 4 / 5 - 190, 0);

            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateString = currentDate.format(formatter);

            System.out.println(dateString);
            watermark = new Phrase(dateString.substring(8, 10), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 - 25,
                    PageSize.A4.getHeight() * 4 / 5 - 245, 0);

            watermark = new Phrase(dateString.substring(5, 7), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 + 2,
                    PageSize.A4.getHeight() * 4 / 5 - 245, 0);

            watermark = new Phrase(dateString.substring(0, 4), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 + 45,
                    PageSize.A4.getHeight() * 4 / 5 - 245, 0);
        }
        else{
            // PART B
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 2 / 3 + 35,
                    PageSize.A4.getHeight() * 4 / 5 - 190, 0);

            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateString = currentDate.format(formatter);

            System.out.println(dateString);
            watermark = new Phrase(dateString.substring(8, 10), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 - 25,
                    PageSize.A4.getHeight() * 4 / 5 - 245, 0);

            watermark = new Phrase(dateString.substring(5, 7), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 + 2,
                    PageSize.A4.getHeight() * 4 / 5 - 245, 0);

            watermark = new Phrase(dateString.substring(0, 4), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 + 45,
                    PageSize.A4.getHeight() * 4 / 5 - 245, 0);


            // PART C
            watermark = new Phrase(String.valueOf(studentCourse.getCriteriaReport().scoreOfItemOne()), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 + 15,
                    PageSize.A4.getHeight() * 4 / 5 - 330, 0);
            watermark = new Phrase(String.valueOf(studentCourse.getCriteriaReport().sumOfItemsFromTwoToSeven()), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 + 15,
                    PageSize.A4.getHeight() * 4 / 5 - 360, 0);
            watermark = new Phrase(String.valueOf(studentCourse.getCriteriaReport().scoreOfEvaluationOfTheReport()), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 + 15,
                    PageSize.A4.getHeight() * 4 / 5 - 390, 0);

            watermark = new Phrase("X", watermarkFont);
            if(studentCourse.getCriteriaReport().isOverallSatisfactory()){
                ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() / 2 + 32,
                        PageSize.A4.getHeight() * 4 / 5 - 438, 0);
            }
            else{
                ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 2 / 3 + 117,
                        PageSize.A4.getHeight() * 4 / 5 - 438, 0);
            }
            watermark = new Phrase(studentCourse.getInstructor().getName(), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() / 2 + 80,
                    PageSize.A4.getHeight() * 4 / 5 - 465, 0);

            System.out.println(dateString);
            watermark = new Phrase(dateString.substring(8, 10), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 - 25,
                    PageSize.A4.getHeight() * 4 / 5 - 245, 0);

            watermark = new Phrase(dateString.substring(5, 7), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 + 2,
                    PageSize.A4.getHeight() * 4 / 5 - 245, 0);

            watermark = new Phrase(dateString.substring(0, 4), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 + 45,
                    PageSize.A4.getHeight() * 4 / 5 - 245, 0);

            watermark = new Phrase("X", watermarkFont);
            if(studentCourse.getCriteriaReport().getRecommendation().equals(Recommendation.recommended)){
                ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() / 8 + 3,
                        PageSize.A4.getHeight() * 4 / 5 - 570, 0);
            }
            else if(studentCourse.getCriteriaReport().getRecommendation().equals(Recommendation.satisfactory)){
                ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() / 8 + 3,
                        PageSize.A4.getHeight() * 4 / 5 - 585, 0);
            }
            else{
                ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() / 8 + 3,
                        PageSize.A4.getHeight() * 4 / 5 - 600, 0);
            }

                /*/
                Image signatureImage = Image.getInstance("src/main/resources/bilkent.png");
                signatureImage.setAbsolutePosition(100, 100);
                signatureImage.scaleToFit(200, 100);

                content.addImage(signatureImage);*/


            // ======PAGE 2 2nd column
            content = stamper.getUnderContent(2);
            watermark = new Phrase("" + studentCourse.getCriteriaReport().getQuestions().get(0).getScore(), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 - 35,
                    PageSize.A4.getHeight() * 4 / 5 + 20, 0);
            watermark = new Phrase("" + studentCourse.getCriteriaReport().getQuestions().get(1).getScore(), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 - 35,
                    PageSize.A4.getHeight() * 4 / 5 - 40, 0);
            watermark = new Phrase("" + studentCourse.getCriteriaReport().getQuestions().get(2).getScore(), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 - 35,
                    PageSize.A4.getHeight() * 4 / 5 - 85, 0);
            watermark = new Phrase("" + studentCourse.getCriteriaReport().getQuestions().get(3).getScore(), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 - 35,
                    PageSize.A4.getHeight() * 4 / 5 - 140, 0);
            watermark = new Phrase("" +studentCourse.getCriteriaReport().getQuestions().get(4).getScore(), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 - 35,
                    PageSize.A4.getHeight() * 4 / 5 - 185, 0);
            watermark = new Phrase("" +studentCourse.getCriteriaReport().getQuestions().get(5).getScore(), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 - 35,
                    PageSize.A4.getHeight() * 4 / 5 - 225, 0);
            watermark = new Phrase("" +studentCourse.getCriteriaReport().getQuestions().get(6).getScore(), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 - 35,
                    PageSize.A4.getHeight() * 4 / 5 - 260, 0);
            watermark = new Phrase("" +studentCourse.getCriteriaReport().getQuestions().get(7).getScore(), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, PageSize.A4.getWidth() * 4 / 5 - 35,
                    PageSize.A4.getHeight() * 4 / 5 - 400, 0);

            // =========first column
            watermark = new Phrase(studentCourse.getCriteriaReport().getQuestions().get(0).getAnswer(), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_LEFT, watermark, PageSize.A4.getWidth() * 3 / 5 - 85,
                    PageSize.A4.getHeight() * 4 / 5 + 20, 0);
            watermark = new Phrase(studentCourse.getCriteriaReport().getQuestions().get(1).getAnswer(), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_LEFT, watermark, PageSize.A4.getWidth() * 3 / 5 - 85,
                    PageSize.A4.getHeight() * 4 / 5 - 40, 0);
            watermark = new Phrase(studentCourse.getCriteriaReport().getQuestions().get(2).getAnswer(), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_LEFT, watermark, PageSize.A4.getWidth() * 3 / 5 - 85,
                    PageSize.A4.getHeight() * 4 / 5 - 85, 0);
            watermark = new Phrase(studentCourse.getCriteriaReport().getQuestions().get(3).getAnswer(), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_LEFT, watermark, PageSize.A4.getWidth() * 3 / 5 - 85,
                    PageSize.A4.getHeight() * 4 / 5 - 140, 0);
            watermark = new Phrase(studentCourse.getCriteriaReport().getQuestions().get(4).getAnswer(), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_LEFT, watermark, PageSize.A4.getWidth() * 3 / 5 - 85,
                    PageSize.A4.getHeight() * 4 / 5 - 185, 0);
            watermark = new Phrase(studentCourse.getCriteriaReport().getQuestions().get(5).getAnswer(), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_LEFT, watermark, PageSize.A4.getWidth() * 3 / 5 - 85,
                    PageSize.A4.getHeight() * 4 / 5 - 225, 0);
            watermark = new Phrase(studentCourse.getCriteriaReport().getQuestions().get(6).getAnswer(), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_LEFT, watermark, PageSize.A4.getWidth() * 3 / 5 - 85,
                    PageSize.A4.getHeight() * 4 / 5 - 260, 0);
            watermark = new Phrase(studentCourse.getCriteriaReport().getQuestions().get(7).getAnswer(), watermarkFont);
            ColumnText.showTextAligned(content, Element.ALIGN_LEFT, watermark, PageSize.A4.getWidth() * 3 / 5 - 85,
                    PageSize.A4.getHeight() * 4 / 5 - 400, 0);
        }
        stamper.close();
        reader.close();

        return outputStream.toByteArray();
    }
}