package com.yulkost.service.service;

import com.yulkost.service.dto.CategoriesDtoToItemsEdit;
import com.yulkost.service.dto.CategoriesDtoToPage;
import com.yulkost.service.dto.mapper.CategoriesDtoToItemsEditMapper;
import com.yulkost.service.dto.mapper.CategoriesDtoToPageWithViewItemsMapper;
import com.yulkost.service.model.Categories;
import com.yulkost.service.repository.CategoriesRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;
    private final CategoriesDtoToItemsEditMapper categoriesDtoToItemsEditMapper;
    private final CategoriesDtoToPageWithViewItemsMapper categoriesDtoToPageWithViewItemsMapper;

    public CategoriesService(CategoriesRepository categoriesRepository,  CategoriesDtoToItemsEditMapper categoriesDtoToItemsEditMapper, CategoriesDtoToPageWithViewItemsMapper categoriesDtoToPageWithViewItemsMapper) {
        this.categoriesRepository = categoriesRepository;
        this.categoriesDtoToItemsEditMapper = categoriesDtoToItemsEditMapper;
        this.categoriesDtoToPageWithViewItemsMapper = categoriesDtoToPageWithViewItemsMapper;
    }

    public void save(Categories categories){
        categoriesRepository.save(categories);
}
    public List<CategoriesDtoToPage> findAllToPage(){

        return categoriesRepository.findAll(Sort.by(Sort.Direction.ASC, "categoriesName"))
                .stream()
                .map(categoriesDtoToPageWithViewItemsMapper)
                .collect(Collectors.toList());
    }
    public List<CategoriesDtoToPage> findAllWithViewItemsToPage(){

        return categoriesRepository.findAllByParentCategory(null,Sort.by(Sort.Direction.ASC, "categoriesName"))
                .stream()
                .map(categoriesDtoToPageWithViewItemsMapper)
        .collect(Collectors.toList());
    }
    public List<CategoriesDtoToItemsEdit> findAllToItemsEdit(){

        return categoriesRepository.findAll(Sort.by(Sort.Direction.ASC, "categoriesName"))
                .stream()
                .map(categoriesDtoToItemsEditMapper)
                .collect(Collectors.toList());
    }
    public List<Categories> findAllCategories(){

        return categoriesRepository.findAll(Sort.by(Sort.Direction.ASC, "categoriesName"));
    }

    public void saveAll(List<Categories> categories) {
        categoriesRepository.saveAll(categories);
    }

    public void deleteById(Long id) {
        categoriesRepository.deleteById(id);
    }
}
