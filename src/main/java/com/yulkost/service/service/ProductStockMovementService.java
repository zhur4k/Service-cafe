package com.yulkost.service.service;

import com.yulkost.service.model.*;
import com.yulkost.service.repository.ProductStockMovementRepository;
import com.yulkost.service.repository.ProductStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductStockMovementService {
    private ProductStockRepository productStockRepository;
    private ProductStockMovementRepository productStockMovementRepository;
    @Autowired
    public void setProductStockRepository(ProductStockRepository productStockRepository) {
        this.productStockRepository = productStockRepository;
    }
    @Autowired
    public void setProductStockMovementRepository(ProductStockMovementRepository productStockMovementRepository) {
        this.productStockMovementRepository = productStockMovementRepository;
    }

    public void saveMovementAdd(ProductStockMovement movement) {
        movement.setDateOfOperation(LocalDateTime.now());
        movement.setTypeOfOperation("add");
        productStockMovementRepository.save(movement);
        ProductStock productStock = productStockRepository.findByProductId(movement.getProduct().getId());
        productStock.setWeight(productStock.getWeight()+ movement.getWeight());
        productStock.setPrice(movement.getPrice());
        productStockRepository.save(productStock);
    }
    public void saveMovementRemove(ProductStockMovement movement) {
        movement.setDateOfOperation(LocalDateTime.now());
        movement.setTypeOfOperation("remove");
        productStockMovementRepository.save(movement);
        ProductStock productStock = productStockRepository.findByProductId(movement.getProduct().getId());
        productStock.setWeight(productStock.getWeight()- movement.getWeight());
        productStockRepository.save(productStock);
    }
    public void saveMovementOrderItem(OrderItems item, ProductStock productStock, ProductWeight productWeight) {
        ProductStockMovement movement = new ProductStockMovement();
        movement.setDateOfOperation(LocalDateTime.now());
        movement.setTypeOfOperation("order");
        movement.setOrderItems(item);
        movement.setProduct(productWeight.getProduct());
        movement.setPrice(productStock.getPrice());
        movement.setWeight(productWeight.getWeight()* item.getQuantity());
        productStockMovementRepository.save(movement);
    }
}
