package com.yulkost.service.service;

import com.yulkost.service.model.Categories;
import com.yulkost.service.model.Items;
import com.yulkost.service.model.Products;
import com.yulkost.service.repository.ProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    private ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }


    public Iterable<Products> findAll() {
        return productsRepository.findAll();
    }

    public void saveAll(List<Products> products) {
        productsRepository.saveAll(products);
    }

    public void save(Products products) {
        productsRepository.save(products);
    }

    public Products findById(Long id) {
        Optional<Products> optionalProducts = productsRepository.findById(id);
        if (optionalProducts.isPresent()) {
            return optionalProducts.get();
        } else {
            throw new RuntimeException("Объект с id=" + id + " не найден");
        }
    }
}
