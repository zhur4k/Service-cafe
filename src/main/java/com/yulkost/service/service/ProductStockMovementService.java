package com.yulkost.service.service;

import com.yulkost.service.model.*;
import com.yulkost.service.repository.ProductStockMovementRepository;
import com.yulkost.service.repository.ProductStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
        movement.setDateOfOperation(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        movement.setTypeOfOperation("add");
        productStockMovementRepository.save(movement);
        ProductStock productStock = productStockRepository.findByProductId(movement.getProduct().getId());
        int totalCost = productStock.getWeight() * productStock.getPrice() + movement.getWeight()  * movement.getPrice();
        int totalWeight = productStock.getWeight()+movement.getWeight();
        int averagePrice = totalCost / totalWeight;
        productStock.setWeight(productStock.getWeight()+ movement.getWeight());
        productStock.setPrice(averagePrice);
        productStockRepository.save(productStock);
    }
    public void saveMovementRemove(ProductStockMovement movement) {
        movement.setDateOfOperation(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        movement.setTypeOfOperation("remove");
        productStockMovementRepository.save(movement);
        ProductStock productStock = productStockRepository.findByProductId(movement.getProduct().getId());
        productStock.setWeight(productStock.getWeight()- movement.getWeight());
        productStockRepository.save(productStock);
    }
    public void saveMovementOrderItem(OrderItems item, ProductStock productStock, ProductWeight productWeight) {
        ProductStockMovement movement = new ProductStockMovement();
        movement.setDateOfOperation(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        movement.setTypeOfOperation("order");
        movement.setOrderItems(item);
        movement.setProduct(productWeight.getProduct());
        movement.setPrice(productStock.getPrice());
        movement.setWeight(productWeight.getWeight()* item.getQuantity());
        productStockMovementRepository.save(movement);
    }
}
