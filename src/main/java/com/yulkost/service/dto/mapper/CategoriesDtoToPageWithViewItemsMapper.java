package com.yulkost.service.dto.mapper;

import com.yulkost.service.dto.CategoriesDtoToPage;
import com.yulkost.service.model.Categories;
import com.yulkost.service.model.Items;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CategoriesDtoToPageWithViewItemsMapper implements Function<Categories, CategoriesDtoToPage> {
    @Override
    public CategoriesDtoToPage apply(Categories categories) {
        List<Items> filteredItems = categories.getItems().stream()
                .filter(Items::getView).toList();
        Long parentId = (categories.getParentCategory() != null) ? categories.getParentCategory().getId() : null;
        return new CategoriesDtoToPage(
                categories.getId(),
                categories.getCategoriesName(),
                filteredItems,
                parentId,
                categories.getChildCategories()
                        .stream()
                        .map(this)
                        .collect(Collectors.toList()));
    }
}
