package com.yulkost.service.dto;

import com.yulkost.service.model.Categories;
import com.yulkost.service.model.Items;

import java.util.List;

public record CategoriesDtoToItemsEdit(
        Long id,
    String categoriesName
){
}
