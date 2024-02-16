package com.yulkost.service.dto.mapper;

import com.yulkost.service.dto.CategoriesDtoToPage;
import com.yulkost.service.model.Categories;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CategoriesDtoToPageMapper implements Function<Categories, CategoriesDtoToPage> {
    @Override
    public CategoriesDtoToPage apply(Categories categories) {
        Long parentId = (categories.getParentCategory() != null) ? categories.getParentCategory().getId() : null;
        return new CategoriesDtoToPage(
                categories.getId(),
                categories.getCategoriesName(),
                categories.getItems(),
                parentId,
                categories.getChildCategories());
    }
}
