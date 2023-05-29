package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.Question;
import com.example.rigel_v1.domain.Users;
import com.example.rigel_v1.repositories.QuestionRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    private final QuestionRepository questionRepository;
    private final UsersController usersController;

    public StatisticsController(QuestionRepository questionRepository, UsersController usersController) {
        this.questionRepository = questionRepository;
        this.usersController = usersController;
    }

    @GetMapping("/get-statistics")
    public /*File*/ void  getStatistics(){
        ArrayList<Integer> questionAverages = questionAverages();
        double generalAverage = calculateGeneralAverageScore(questionAverages);
        int[][] array = new int[8][11];
        array = calculateNumberOfStudentGotXFromQY();
        System.out.println(array);

        try (Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");

            // Define cell styles for different sections
            CellStyle sectionHeaderStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font sectionHeaderFont = workbook.createFont();
            sectionHeaderFont.setBold(true);
            sectionHeaderStyle.setFont(sectionHeaderFont);

            // Write question averages to Excel
            Row questionAveragesRow = sheet.createRow(0);
            CellStyle questionAveragesStyle = workbook.createCellStyle();
            questionAveragesStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("0.00"));
            for (int i = 0; i < questionAverages.size(); i++) {
                Cell cell = questionAveragesRow.createCell(i);
                if (cell == null) {
                    cell = questionAveragesRow.createCell(i, CellType.NUMERIC);
                }
                cell.setCellValue(questionAverages.get(i));
                cell.setCellStyle(questionAveragesStyle);
            }
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, questionAverages.size() - 1));

            // Write general average to Excel
            Row generalAverageRow = sheet.createRow(1);
            Cell generalAverageCell = generalAverageRow.createCell(0);
            if (generalAverageCell == null) {
                generalAverageCell = generalAverageRow.createCell(0, CellType.NUMERIC);
            }
            generalAverageCell.setCellValue(generalAverage);
            generalAverageCell.setCellStyle(questionAveragesStyle);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));

            // Write 2D array to Excel
            CellStyle arrayStyle = workbook.createCellStyle();
            arrayStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("0"));
            for (int i = 0; i < array.length; i++) {
                Row row = sheet.createRow(i + 3);
                for (int j = 0; j < array[i].length; j++) {
                    Cell cell = row.createCell(j);
                    if (cell == null) {
                        cell = row.createCell(j, CellType.NUMERIC);
                    }
                    cell.setCellValue(array[i][j]);
                    cell.setCellStyle(arrayStyle);
                }
            }

            // Apply section header style to the first row and column
            for (int i = 0; i < array[0].length; i++) {
                Cell cell = sheet.getRow(0).getCell(i);
                if (cell != null) {
                    cell.setCellStyle(sectionHeaderStyle);
                }
            }
            for (int i = 0; i < array.length; i++) {
                Cell cell = sheet.getRow(i + 3).getCell(0);
                if (cell != null) {
                    cell.setCellStyle(sectionHeaderStyle);
                }
            }

            // Save the Excel file to the desktop
            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            try (FileOutputStream fileOutputStream = new FileOutputStream(desktopPath + "data.xls")) {
                workbook.write(fileOutputStream);
                System.out.println("Excel file created successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//generates graph
/*
        // Create a dataset for the graph
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                String series = "Series " + i;
                String category = "Category " + j;
                int value = array[i][j];
                dataset.addValue(value, series, category);
            }
        }

        // Create the chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Graph from 2D Array",
                "X-Axis",
                "Y-Axis",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Customize the chart
        chart.setBackgroundPaint(Color.WHITE);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLACK);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setShadowVisible(false);
        renderer.setMaximumBarWidth(0.15);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        domainAxis.setLowerMargin(0.02);
        domainAxis.setUpperMargin(0.02);
        domainAxis.setCategoryMargin(0.1);
        domainAxis.setTickLabelFont(new Font("SansSerif", Font.BOLD, 12));

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setLabelFont(new Font("SansSerif", Font.BOLD, 12));

        plot.setAxisOffset(new RectangleInsets(5, 5, 5, 5));
        plot.setOutlineVisible(false);

        GradientPaint gradientPaint = new GradientPaint(0.0f, 0.0f, Color.LIGHT_GRAY, 0.0f, 0.0f, Color.WHITE);
        renderer.setSeriesPaint(0, gradientPaint);

        // Create a BufferedImage to hold the chart
        BufferedImage bufferedImage = chart.createBufferedImage(800, 600);

        // Save the chart as an image file
        File imageFile = new File("graph.png");
        try {
            ImageIO.write(bufferedImage, "png", imageFile);
            System.out.println("Graph saved as " + imageFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private int[][] calculateNumberOfStudentGotXFromQY() {
        int[][] result = new int[8][11];
        for(int i = 1; i < questionRepository.count(); i++){
            Optional<Question> optional = questionRepository.findById(Long.valueOf(i));
            if(optional.isPresent()){
                Question q = optional.get();
                int rowValue = ((q.getId().intValue()-1)%8);
                if(q.getScore() != -1){
                    result[rowValue][q.getScore()] += 1;
                }
            }
        }
        return result;
    }

    private double calculateGeneralAverageScore(ArrayList<Integer> questionAverages) {
        int currValue = 0;
        for(int i = 0; i < 8; i++){
            currValue += questionAverages.get(i) * i;
        }
        return ((double) currValue)/questionAverages.size();
    }

    private ArrayList<Integer> questionAverages() {
        int q1Score = 0, q2Score = 0, q3Score = 0, q4Score = 0, q5Score = 0, q6Score = 0, q7Score = 0, q8Score = 0;
        for(int i = 1; i <= questionRepository.count(); i++){
            Optional<Question> optional = questionRepository.findById(Long.valueOf(i));
            if(optional.isPresent()){
                Question q = optional.get();
                if(q.getScore() == -1){
                    continue;
                }
                switch((i-1)%8){
                    case 0:
                        q1Score += q.getScore();
                        break;
                    case 1:
                        q2Score += q.getScore();
                        break;
                    case 2:
                        q3Score += q.getScore();
                        break;
                    case 3:
                        q4Score += q.getScore();
                        break;
                    case 4:
                        q5Score += q.getScore();
                        break;
                    case 5:
                        q6Score += q.getScore();
                        break;
                    case 6:
                        q7Score += q.getScore();
                        break;
                    case 7:
                        q8Score += q.getScore();
                        break;
                    default: break;
                }

            }
        }
        return new ArrayList<>(Arrays.asList(q1Score, q2Score, q3Score, q4Score, q5Score, q6Score, q7Score, q8Score));
    }
}

