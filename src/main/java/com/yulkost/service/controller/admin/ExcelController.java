//package com.yulkost.service.controller.admin;
//import com.yulkost.service.dto.DateFromPage;
//import com.yulkost.service.service.ExcelService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/admin/stock/excel")
//public class ExcelController {
//    private ExcelService excelService;
//
//    @Autowired
//    public void setExcelService(ExcelService excelService) {
//        this.excelService = excelService;
//    }
//
//    @PostMapping("/1")
//    public ResponseEntity<?> downloadExcel1(@RequestBody DateFromPage dateFromPage) throws IOException {
//        // Создайте Excel-файл (например, с использованием Apache POI)
//        byte[] excelBytes = excelService.createExcelFile1(
//                dateFromPage.startDate(),
//                dateFromPage.endDate()
//        );
//
//        // Создайте ресурс из байтов Excel-файла
//        ByteArrayResource resource = new ByteArrayResource(excelBytes);
//
//        // Установите заголовки ответа
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx");
//
//        // Установите правильный Content-Type для Excel-файла
//        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
//
//        // Отправьте файл пользователю
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentLength(excelBytes.length)
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(resource);
//    }@PostMapping("/2")
//    public ResponseEntity<?> downloadExcel2(@RequestBody DateFromPage dateFromPage) throws IOException {
//        // Создайте Excel-файл (например, с использованием Apache POI)
//        byte[] excelBytes = excelService.createExcelFile2(
//                dateFromPage.startDate(),
//                dateFromPage.endDate()
//        );
//
//        // Создайте ресурс из байтов Excel-файла
//        ByteArrayResource resource = new ByteArrayResource(excelBytes);
//
//        // Установите заголовки ответа
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx");
//
//        // Установите правильный Content-Type для Excel-файла
//        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
//
//        // Отправьте файл пользователю
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentLength(excelBytes.length)
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(resource);
//    }
//
//
//}
