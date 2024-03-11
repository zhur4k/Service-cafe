package com.yulkost.service.dto.mapper;

import com.yulkost.service.dto.CategoriesDtoToMenu;
import com.yulkost.service.model.Categories;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CategoriesDtoToMenuMapper implements Function<Categories, CategoriesDtoToMenu> {
    @Override
    public CategoriesDtoToMenu apply(Categories categories) {
        return new CategoriesDtoToMenu(
                categories.getCategoriesName(),
                categories.getItems(),
                categories.getChildCategories()
                        .stream()
                        .map(this)
                        .collect(Collectors.toList()));
    }
}
