package com.yulkost.service.service;

import com.yulkost.service.model.Categories;
import com.yulkost.service.repository.CategoriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {
    public CategoriesRepository categoriesRepository;

    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }
    public void save(Categories categories){
        categoriesRepository.save(categories);
}
    public List<Categories> findAll(){
        return categoriesRepository.findAll();
    }

    public void saveAll(List<Categories> categories) {
        categoriesRepository.saveAll(categories);
    }
}
