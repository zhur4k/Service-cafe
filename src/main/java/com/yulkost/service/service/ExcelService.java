//package com.yulkost.service.service;
//
//import com.yulkost.service.model.OrderItems;
//import com.yulkost.service.model.Orders;
//import com.yulkost.service.model.ProductStockMovement;
//import com.yulkost.service.model.Products;
//import com.yulkost.service.repository.OrdersRepository;
//import com.yulkost.service.repository.ProductsRepository;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.text.DecimalFormatSymbols;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//@Service
//public class ExcelService {
//    private OrdersRepository ordersRepository;
//    private ProductsRepository productsRepository;
//
//    @Autowired
//    public void setOrdersRepository(OrdersRepository ordersRepository) {
//        this.ordersRepository = ordersRepository;
//    }
//
//    @Autowired
//    public void setProductsRepository(ProductsRepository productsRepository) {
//        this.productsRepository = productsRepository;
//    }
//
//    public byte[] createExcelFile1(LocalDate startDate, LocalDate endDate) throws IOException {
//        try (Workbook workbook = new XSSFWorkbook();
//             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
//
//            Sheet sheet = workbook.createSheet("Report");
//            // Create header row
//
//
//            List<Orders> orders = ordersRepository.findByDateBetween(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
//            List<OrderItems> orderItems = mergeSameOrderItems(orders);
//
//
//            // Создаем стиль для центрирования текста
//            CellStyle centerAlignStyle = workbook.createCellStyle();
//            centerAlignStyle.setAlignment(HorizontalAlignment.CENTER);
//
//            CellStyle dateStyle = workbook.createCellStyle();
//            CreationHelper createHelper = workbook.getCreationHelper();
//            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
//
//
//            List<Products> products = productsRepository.findAll();
//
//            addProductsAndInfoToSheet(products, sheet, centerAlignStyle);
//            addInfoAboutOrderItem(orderItems,products,sheet,centerAlignStyle);
//
//            // Автоматическое растягивание колонок под содержимое
//            for (int columnIndex = 0; columnIndex < sheet.getRow(2).getLastCellNum(); columnIndex++) {
//                sheet.autoSizeColumn(columnIndex);
//            }
//            workbook.write(bos);
//
//            return bos.toByteArray();
//        } catch (Exception e) {
//            e.printStackTrace(); // Log the exception
//            throw new IOException("Error creating Excel file", e);
//        }
//    }
//    public byte[] createExcelFile2(LocalDate startDate, LocalDate endDate) throws IOException {
//        try (Workbook workbook = new XSSFWorkbook();
//             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
//
//            Sheet sheet = workbook.createSheet("Report");
//            // Create header row
//
//
//            List<Orders> orders = ordersRepository.findByDateBetween(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
//            List<OrderItems> orderItems = mergeSameOrderItems(orders);
//
//
//            // Создаем стиль для центрирования текста
//            CellStyle centerAlignStyle = workbook.createCellStyle();
//            centerAlignStyle.setAlignment(HorizontalAlignment.CENTER);
//
//            CellStyle dateStyle = workbook.createCellStyle();
//            CreationHelper createHelper = workbook.getCreationHelper();
//            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
//
//
//            List<Products> products = productsRepository.findAll();
//
//            addProductsAndInfoToSheet(products, sheet, centerAlignStyle);
//            addInfoAboutOrderItem(orderItems,products,sheet,centerAlignStyle);
//
//            // Автоматическое растягивание колонок под содержимое
//            for (int columnIndex = 0; columnIndex < sheet.getRow(2).getLastCellNum(); columnIndex++) {
//                sheet.autoSizeColumn(columnIndex);
//            }
//            workbook.write(bos);
//
//            return bos.toByteArray();
//        } catch (Exception e) {
//            e.printStackTrace(); // Log the exception
//            throw new IOException("Error creating Excel file", e);
//        }
//    }
//    private void addInfoAboutOrderItem(List<OrderItems> orderItems,List<Products> products,Sheet sheet,CellStyle cellStyle) {
//        int i = 0;
//        for (OrderItems item :
//                orderItems) {
//            Row row = sheet.createRow(4 + i);
//
//            createCell(0,row,cellStyle,item.getItems().getNameOfItems());
//
//            createCell(1,row,cellStyle,item.getItems().getCategories().getCategoriesName());
//
//            createCell(2,row,cellStyle,item.getDateOfItemChange().toString());
//
//            createCell(3,row,cellStyle,Integer.toString(item.getQuantity()));
//
//            int j = 0;
//            for (Products product :
//                    products) {
//                for (ProductStockMovement productStockMovement :
//                        item.getProductStockMovements()) {
//                    if (Objects.equals(product.getId(), productStockMovement.getProduct().getId())) {
//
//                        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
//                        symbols.setDecimalSeparator('.');
//                        createCell(5 + j,
//                                row,
//                                cellStyle,
//                                new DecimalFormat("0.000", symbols).
//                                        format(((float) productStockMovement.getWeight()) / 1000 / item.getQuantity())
//                                );
//
//                        createCell(5 + j,row,cellStyle,productStockMovement.getWeightToPage());
//                    }
//                }
//                j += 2;
//            }
//            i++;
//        }
//    }
//
//    private List<OrderItems> mergeSameOrderItems(List<Orders> orders) {
//        List<OrderItems> orderItems = new ArrayList<>();
//        boolean flag;
//        for (Orders order :
//                orders) {
//            for (OrderItems orderItemInOrder :
//                    order.getOrderItems()) {
//                if (orderItemInOrder.getItems().getTypeOfItem()) {
//                    flag = false;
//                    for (OrderItems orderItemInExcelArray :
//                            orderItems) {
//                        if (orderItemInOrder.getDateOfItemChange().isEqual(orderItemInExcelArray.getDateOfItemChange()) && orderItemInOrder.getItems().equals(orderItemInExcelArray.getItems())) {
//                            orderItemInExcelArray.setQuantity(orderItemInOrder.getQuantity() + orderItemInExcelArray.getQuantity());
//                            flag = true;
//                            break;
//                        }
//                    }
//                    if (!flag) {
//                        orderItems.add(orderItemInOrder);
//                    }
//                }
//            }
//        }
//        return orderItems;
//    }
//
//    private void addProductsAndInfoToSheet(List<Products> products, Sheet sheet, CellStyle centerAlignStyle) {
//        Row row0 = sheet.createRow(2);
//        Row row1 = sheet.createRow(3);
//
//        row0.createCell(0).setCellValue("Наименование");
//        row0.createCell(1).setCellValue("Категория");
//        row0.createCell(2).setCellValue("Дата");
//        row0.createCell(3).setCellValue("Кол-во");
//        int i = 0;
//        for (Products product : products) {
//            // Создаем ячейку для имени продукта
//            createCell(4 + i,row0,centerAlignStyle,product.getName());
//
//            // Применяем объединение ячеек для имени продукта
//            sheet.addMergedRegion(new CellRangeAddress(2, 2, 4 + i, 5 + i));
//            // Автоматическое растягивание ячейки под содержимое
//            sheet.autoSizeColumn(4 + i);
//
//
//            // Создаем ячейку "нормы"
//            createCell(4 + i,row1,centerAlignStyle,"норм");
//
//            // Создаем ячейку "факта"
//            createCell(5 + i,row1,centerAlignStyle,"факт");
//
//            i += 2;
//        }
//    }
//
//    private void createCell(int column, Row row, CellStyle cellStyle, String cellValue) {
//        // Создаем ячейку "факта"
//        Cell cellFact = row.createCell(column);
//        cellFact.setCellValue(cellValue);
//        cellFact.setCellStyle(cellStyle);
//    }
//}
