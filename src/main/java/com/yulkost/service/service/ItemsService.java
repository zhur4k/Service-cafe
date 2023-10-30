package com.yulkost.service.service;

import com.yulkost.service.model.Categories;
import com.yulkost.service.model.Items;
import com.yulkost.service.repository.CategoriesRepository;
import com.yulkost.service.repository.ItemsRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemsService {
    public ItemsRepository itemsRepository;
    public CategoriesRepository categoryRepository;

    public ItemsService(ItemsRepository itemsRepository, CategoriesRepository categoryRepository) {
        this.itemsRepository = itemsRepository;
        this.categoryRepository = categoryRepository;
    }

    public Iterable<Categories> findAllCategories(){
        return categoryRepository.findAll();
    }
    public Iterable<Items> findAll(){
        return itemsRepository.findAll();
    }
}
