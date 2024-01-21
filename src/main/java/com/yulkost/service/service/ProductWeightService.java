package com.yulkost.service.service;

import com.yulkost.service.model.ProductWeight;
import com.yulkost.service.repository.ProductWeightRepository;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ProductWeightService {
    private ProductWeightRepository productWeightRepository;

    public ProductWeightService(ProductWeightRepository productWeightRepository) {
        this.productWeightRepository = productWeightRepository;
    }
    public void saveAll(List<ProductWeight> productWeight) {
        Iterator<ProductWeight> iterator = productWeight.iterator();
        while (iterator.hasNext()) {
            ProductWeight productWeight1 = iterator.next();
            if (productWeight1.getWeight() <= 0) {
                iterator.remove();
                productWeightRepository.delete(productWeight1);
            }
        }
        if (!productWeight.isEmpty()){
            productWeightRepository.saveAll(productWeight);
        }
    }

    public void save(ProductWeight productWeight) {
        productWeightRepository.save(productWeight);
    }
}
