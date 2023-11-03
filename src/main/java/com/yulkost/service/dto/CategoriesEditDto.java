package com.yulkost.service.dto;



import com.yulkost.service.model.Categories;
import com.yulkost.service.model.User;

import java.util.ArrayList;
import java.util.List;

public class CategoriesEditDto {
    private final List<Categories> category;

    public CategoriesEditDto(){
        this.category = new ArrayList<>();
    }
    public CategoriesEditDto(List<Categories> category) {
        this.category = category;
    }

    public List<Categories> getCategory() {
        return category;
    }

}
