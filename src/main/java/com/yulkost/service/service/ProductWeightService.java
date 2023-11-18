package com.yulkost.service.service;

import com.yulkost.service.model.ProductWeight;
import com.yulkost.service.repository.ProductWeightRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductWeightService {
    private ProductWeightRepository productWeightRepository;

    public ProductWeightService(ProductWeightRepository productWeightRepository) {
        this.productWeightRepository = productWeightRepository;
    }

    public void save(ProductWeight productWeight) {
        productWeightRepository.save(productWeight);
    }
}
