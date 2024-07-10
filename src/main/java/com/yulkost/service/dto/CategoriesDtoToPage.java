package com.yulkost.service.dto;

import com.yulkost.service.model.Items;

import java.util.List;

public record CategoriesDtoToPage(
    Long id,
    String categoriesName,
    List<Items> items,
    Long parentId,
    List<CategoriesDtoToPage> childCategories
){
}
