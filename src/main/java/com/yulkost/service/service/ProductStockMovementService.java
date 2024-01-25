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

        ProductStock productStock = productStockRepository.findByProductId(movement.getProduct().getId());
        movement.setBalanceWeight(productStock.getWeight());
        movement.setPriceOnStock(productStock.getPrice());
        productStockMovementRepository.save(movement);
        if(productStock.getWeight()>0){
            int totalCost = productStock.getWeight() * productStock.getPrice() + movement.getWeight()  * movement.getPriceMovement();
            int totalWeight = productStock.getWeight()+movement.getWeight();
            int averagePrice = totalCost / totalWeight;
            productStock.setPrice(averagePrice);
        }
        else{
            productStock.setPrice(movement.getPriceMovement());
        }
        productStock.setWeight(productStock.getWeight()+ movement.getWeight());
        productStockRepository.save(productStock);
    }
    public void saveMovementRemove(ProductStockMovement movement) {
        movement.setDateOfOperation(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        movement.setTypeOfOperation("remove");
        ProductStock productStock = productStockRepository.findByProductId(movement.getProduct().getId());
        movement.setPriceMovement(productStock.getPrice());
        movement.setPriceOnStock(productStock.getPrice());
        movement.setBalanceWeight(productStock.getWeight());
        productStockMovementRepository.save(movement);
        productStock.setWeight(productStock.getWeight()- movement.getWeight());
        productStockRepository.save(productStock);
    }
    public void saveMovementOrderItem(OrderItems item, ProductStock productStock, ProductWeight productWeight, int quantity) {
        ProductStockMovement movement = new ProductStockMovement();
        movement.setDateOfOperation(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        movement.setTypeOfOperation("order");
        movement.setOrderItems(item);
        movement.setProduct(productWeight.getProduct());
        movement.setPriceOnStock(productStock.getPrice());
        movement.setPriceMovement(productStock.getPrice());
        movement.setWeight(productWeight.getWeight()*quantity);
        movement.setBalanceWeight(productStock.getWeight());
        productStockMovementRepository.save(movement);
    }
}
