package com.yulkost.service.dto;

import com.yulkost.service.model.ProductStock;
import java.util.ArrayList;
import java.util.List;

public class ProductStockEditDto {
    private final List<ProductStock> productStocks;

    public ProductStockEditDto(){
        this.productStocks = new ArrayList<>();
    }
    public ProductStockEditDto(List<ProductStock> productStocks) {
        this.productStocks = productStocks;
    }

    public List<ProductStock> getProductStock() {
        return productStocks;
    }

}
