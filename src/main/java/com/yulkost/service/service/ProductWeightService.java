package com.yulkost.service.service;

import com.yulkost.service.model.Items;
import com.yulkost.service.model.ProductWeight;
import com.yulkost.service.repository.ProductWeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductWeightService {
    private ProductWeightRepository productWeightRepository;
    private ItemsService itemsService;
    @Autowired
    public void setItemsService(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    public ProductWeightService(ProductWeightRepository productWeightRepository) {
        this.productWeightRepository = productWeightRepository;
    }
    public void saveAll(List<ProductWeight> productWeight) {
        itemsService.setChangeTime(productWeight.get(0).getItem().getId());
        productWeightRepository.saveAll(productWeight);
    }

    public void save(ProductWeight productWeight) {
        itemsService.setChangeTime(productWeight.getItem().getId());
        productWeightRepository.save(productWeight);
    }
}
