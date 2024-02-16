package com.yulkost.service.service;

import com.yulkost.service.dto.CategoriesDtoToPage;
import com.yulkost.service.dto.mapper.CategoriesDtoToPageMapper;
import com.yulkost.service.model.Categories;
import com.yulkost.service.repository.CategoriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;
    private final CategoriesDtoToPageMapper categoriesDtoToPageMapper;

    public CategoriesService(CategoriesRepository categoriesRepository, CategoriesDtoToPageMapper categoriesDtoToPageMapper) {
        this.categoriesRepository = categoriesRepository;
        this.categoriesDtoToPageMapper = categoriesDtoToPageMapper;
    }

    public void save(Categories categories){
        categoriesRepository.save(categories);
}
    public List<CategoriesDtoToPage> findAll(){

        return categoriesRepository.findAll()
                .stream()
                .map(categoriesDtoToPageMapper)
        .collect(Collectors.toList());
    }
    public List<Categories> findAllCategories(){

        return categoriesRepository.findAll();
    }

    public void saveAll(List<Categories> categories) {
        categoriesRepository.saveAll(categories);
    }
}
