package com.yulkost.service.dto.mapper;

import com.yulkost.service.dto.CategoriesDtoToItemsEdit;
import com.yulkost.service.model.Categories;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CategoriesDtoToItemsEditMapper implements Function<Categories, CategoriesDtoToItemsEdit> {
    @Override
    public CategoriesDtoToItemsEdit apply(Categories categories) {
        return new CategoriesDtoToItemsEdit(
                categories.getId(),
                categories.getCategoriesName());
    }
}
