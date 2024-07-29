package com.yulkost.service.service;

import com.yulkost.service.dto.CategoriesDtoToPage;
import com.yulkost.service.model.Items;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
    private final CategoriesService categoriesService;

    public MenuService(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    public String getStringMenu() {
        StringBuilder menu = new StringBuilder();
        for (CategoriesDtoToPage categoriesDtoToPage : categoriesService.findAllWithViewItemsToPage()) {
            menu.append(getMenu(categoriesDtoToPage, new StringBuilder()));
        }
        System.out.println(menu.toString());
        return menu.toString();
    }

    private String getMenu(CategoriesDtoToPage category, StringBuilder pass) {
        StringBuilder menu = new StringBuilder();
        menu.append(pass)
                .append(category.categoriesName())
                .append("\n");  // Add newline for better readability
        StringBuilder newPass = new StringBuilder(pass).append("-");  // Create new StringBuilder for recursive call
        for (Items item : category.items()) {
            menu.append(newPass)
                    .append(item.getNameOfItems());
            if (item.getProductVolume() != null) {
                menu.append(" ")
                        .append(item.getProductVolume());  // Assuming this should display the volume
            }
            menu.append("\n");  // Add newline for better readability
        }
        for (CategoriesDtoToPage subCategory : category.childCategories()) {
            menu.append(getMenu(subCategory, newPass));
        }
        return menu.toString();
    }
}
