package com.yulkost.service.dto;

import com.yulkost.service.model.Items;

import java.util.List;

public record CategoriesDtoToMenu(
    String categoriesName,
    List<Items> items,
    List<CategoriesDtoToMenu> childCategories
){
}
