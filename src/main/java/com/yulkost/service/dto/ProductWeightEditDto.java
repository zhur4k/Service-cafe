package com.yulkost.service.dto;

import com.yulkost.service.model.ProductWeight;
import com.yulkost.service.model.Products;

import java.util.ArrayList;
import java.util.List;

public class ProductWeightEditDto {
    private final List<ProductWeight> productWeights;

    public ProductWeightEditDto(){
        this.productWeights = new ArrayList<>();
    }
    public ProductWeightEditDto(List<ProductWeight> products) {
        this.productWeights = products;
    }

    public List<ProductWeight> getProductWeights() {
        return productWeights;
    }

}
