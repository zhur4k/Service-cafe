package com.yulkost.service.dto;



import com.yulkost.service.model.Categories;
import com.yulkost.service.model.Products;

import java.util.ArrayList;
import java.util.List;

public class ProductsEditDto {
    private final List<Products> products;

    public ProductsEditDto(){
        this.products = new ArrayList<>();
    }
    public ProductsEditDto(List<Products> products) {
        this.products = products;
    }

    public List<Products> getProducts() {
        return products;
    }

}
