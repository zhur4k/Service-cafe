package com.yulkost.service.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Service
public class ExcelService {
    public byte[] createExcelFile(LocalDate startDate, LocalDate endDate) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Report");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ШАУРМА");
            headerRow.createCell(1).setCellValue("К-во");
            headerRow.createCell(2).setCellValue("Лаваш норм");
            headerRow.createCell(3).setCellValue("Лаваш факт");
            headerRow.createCell(4).setCellValue("Курица норм");
            headerRow.createCell(5).setCellValue("Курица факт");
            headerRow.createCell(6).setCellValue("Капуста норм");
            headerRow.createCell(7).setCellValue("Капуста факт");

            // Example data
            String[] shawarmaTypes = {"- чикен", "- барбекю", "- Турецкая", "- Мексикан", "- Грибная", "- из говядины"};
            int[] quantities = {34, 16, 15, 15, 20, 0};
            double[] lavashNorm = {34, 16, 15, 15, 20, 0}; // Replace with actual data
            double[] lavashFact = {1, 1, 1, 1, 1, 1}; // Replace with actual data
            double[] chickenNorm = {0.12, 0.12, 0.12, 0.12, 0.14, 0}; // Replace with actual data
            double[] chickenFact = {4.08, 1.92, 1.8, 1.8, 2.8, 0.1}; // Replace with actual data
            double[] cabbageNorm = {0.1, 0.1, 0.1, 0.1, 0.05, 0}; // Replace with actual data
            double[] cabbageFact = {3.4, 1.6, 1.5, 1.5, 1, 0}; // Replace with actual data

            // Populate the table with data
            for (int i = 0; i < shawarmaTypes.length; i++) {
                Row dataRow = sheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue(shawarmaTypes[i]);
                dataRow.createCell(1).setCellValue(quantities[i]);
                dataRow.createCell(2).setCellValue(lavashNorm[i]);
                dataRow.createCell(3).setCellValue(lavashFact[i]);
                dataRow.createCell(4).setCellValue(chickenNorm[i]);
                dataRow.createCell(5).setCellValue(chickenFact[i]);
                dataRow.createCell(6).setCellValue(cabbageNorm[i]);
                dataRow.createCell(7).setCellValue(cabbageFact[i]);
            }

            // Add a row for totals
            Row totalRow = sheet.createRow(shawarmaTypes.length + 1);
            totalRow.createCell(0).setCellValue("Итого");
            totalRow.createCell(1).setCellValue(100);
            totalRow.createCell(2).setCellFormula("SUM(C2:C" + (shawarmaTypes.length + 1) + ")");
            totalRow.createCell(3).setCellFormula("SUM(D2:D" + (shawarmaTypes.length + 1) + ")");
            totalRow.createCell(4).setCellFormula("SUM(E2:E" + (shawarmaTypes.length + 1) + ")");
            totalRow.createCell(5).setCellFormula("SUM(F2:F" + (shawarmaTypes.length + 1) + ")");
            totalRow.createCell(6).setCellFormula("SUM(G2:G" + (shawarmaTypes.length + 1) + ")");
            totalRow.createCell(7).setCellFormula("SUM(H2:H" + (shawarmaTypes.length + 1) + ")");

            // Add a row for costs (use 0 as a placeholder)
            Row costRow = sheet.createRow(shawarmaTypes.length + 2);
            costRow.createCell(0).setCellValue("Затраты");
            for (int i = 1; i <= 7; i++) {
                costRow.createCell(i).setCellValue(0); // or any other value as a placeholder
            }

            workbook.write(bos);

            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            throw new IOException("Error creating Excel file", e);
        }
    }


}
