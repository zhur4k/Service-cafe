package com.yulkost.service.service;

import com.yulkost.service.model.OrderItems;
import com.yulkost.service.model.Orders;
import com.yulkost.service.model.ProductStockMovement;
import com.yulkost.service.model.Products;
import com.yulkost.service.repository.OrdersRepository;
import com.yulkost.service.repository.ProductsRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ExcelService {
    private OrdersRepository ordersRepository;
    private ProductsRepository productsRepository;

    @Autowired
    public void setOrdersRepository(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Autowired
    public void setProductsRepository(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public byte[] createExcelFile(LocalDate startDate, LocalDate endDate) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Report");
            // Create header row
            Row row0 = sheet.createRow(2);
            Row row1 = sheet.createRow(3);

            row0.createCell(0).setCellValue("Наименование");
            row0.createCell(1).setCellValue("Категория");
            row0.createCell(2).setCellValue("Дата");
            row0.createCell(3).setCellValue("Кол-во");

            List<Orders> orders = ordersRepository.findByDateBetween(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
            List<OrderItems> orderItems = new ArrayList<>();
            boolean flag;
            for (Orders order :
                    orders) {
                for (OrderItems orderItemInOrder :
                        order.getOrderItems()) {
                    if(orderItemInOrder.getItems().getTypeOfItem()){
                        flag=false;
                        for (OrderItems orderItemInExcelArray :
                                orderItems) {
                            if( orderItemInOrder.getDateOfItemChange().isEqual(orderItemInExcelArray.getDateOfItemChange())&&orderItemInOrder.getItems().equals(orderItemInExcelArray.getItems())){
                                orderItemInExcelArray.setQuantity(orderItemInOrder.getQuantity()+orderItemInExcelArray.getQuantity());
                                flag = true;
                                break;
                            }
                        }
                        if (!flag){
                            orderItems.add(orderItemInOrder);
                        }
                    }
                }
            }
            List<Products> products = productsRepository.findAll();
// Создаем стиль для центрирования текста
            CellStyle centerAlignStyle = workbook.createCellStyle();
            centerAlignStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle dateStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
            int i = 0;
            for (Products product : products) {
                // Создаем ячейку для имени продукта
                Cell cellProductName = row0.createCell(4 + i);
                cellProductName.setCellValue(product.getName());
                cellProductName.setCellStyle(centerAlignStyle);

                // Применяем объединение ячеек для имени продукта
                sheet.addMergedRegion(new CellRangeAddress(2, 2, 4 + i, 5 + i));
                // Автоматическое растягивание ячейки под содержимое
                sheet.autoSizeColumn(4 + i);


                // Создаем ячейку "нормы"
                Cell cellNorm = row1.createCell(4 + i);
                cellNorm.setCellValue("норм");
                cellNorm.setCellStyle(centerAlignStyle);

                // Создаем ячейку "факта"
                Cell cellFact = row1.createCell(5 + i);
                cellFact.setCellValue("факт");
                cellFact.setCellStyle(centerAlignStyle);

                i += 2;
            }
            i=0;
            for (OrderItems item :
                    orderItems) {
                Row row2 = sheet.createRow(4+i);

                Cell cellName = row2.createCell(0);
                cellName.setCellValue(item.getItems().getNameOfItems());
                cellName.setCellStyle(centerAlignStyle);

                Cell cellCategory = row2.createCell(1);
                cellCategory.setCellValue(item.getItems().getCategories().getCategoriesName());
                cellCategory.setCellStyle(centerAlignStyle);

                Cell cellDate = row2.createCell(2);
                cellDate.setCellValue(item.getDateOfItemChange());
                cellDate.setCellStyle(dateStyle);

                Cell cellQuantity = row2.createCell(3);
                cellQuantity.setCellValue(item.getQuantity());
                cellQuantity.setCellStyle(centerAlignStyle);
                int j=0;
                for (Products product :
                        products) {
                    for (ProductStockMovement productStockMovement:
                            item.getProductStockMovements()) {
                        if(Objects.equals(product.getId(), productStockMovement.getProduct().getId())){

                            Cell cellNorm = row2.createCell(4 + j);

                            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                            symbols.setDecimalSeparator('.');
                            cellNorm.setCellValue(
                                    new DecimalFormat("0.000",symbols).format(((float)productStockMovement.getWeight()) / 1000/ item.getQuantity()));
                            cellNorm.setCellStyle(centerAlignStyle);

                            Cell cellFact = row2.createCell(5 + j);
                            cellFact.setCellValue(productStockMovement.getWeightToPage());
                            cellFact.setCellStyle(centerAlignStyle);
                        }
                    }
                    j+=2;
                }
                i++;
            }

            // Автоматическое растягивание колонок под содержимое
            for (int columnIndex = 0; columnIndex < row0.getLastCellNum(); columnIndex++) {
                sheet.autoSizeColumn(columnIndex);
            }
            workbook.write(bos);

            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            throw new IOException("Error creating Excel file", e);
        }
    }
    private String getColumnName(int columnIndex) {
        StringBuilder columnName = new StringBuilder();
        while (columnIndex >= 0) {
            int remainder = columnIndex % 26;
            columnName.insert(0, (char) ('A' + remainder));
            columnIndex = (columnIndex / 26) - 1;
        }
        return columnName.toString();
    }

}
