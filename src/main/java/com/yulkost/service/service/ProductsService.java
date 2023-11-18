package com.yulkost.service.service;

import com.yulkost.service.model.Products;
import com.yulkost.service.repository.ProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {
    private ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }


    public Iterable<Products> findAll() {
        return productsRepository.findAll();
    }
}
