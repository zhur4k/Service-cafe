package com.yulkost.service.dto;

import com.yulkost.service.model.Categories;
import com.yulkost.service.model.Items;

import java.util.List;

public record CategoriesDtoToPage (
        Long id,
    String categoriesName,
    List<Items> items,
    Long parentCategory,
    List<Categories> childCategories
){
}
