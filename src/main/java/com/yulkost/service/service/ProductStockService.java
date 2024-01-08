package com.yulkost.service.service;

import com.yulkost.service.model.*;
import com.yulkost.service.repository.ProductStockMovementRepository;
import com.yulkost.service.repository.ProductStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class ProductStockService {
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

    public void writeOffProductFromStock(Orders order){
        for (OrderItems orderItem : order.getOrderItems()) {
            for (ProductWeight productWeight : orderItem.getItems().getProductsWeight()) {
                ProductStock productStock = productStockRepository.findByProductId(productWeight.getProduct().getId());
                if(productStock==null) {
                    productStock = new ProductStock();
                    productStock.setProduct(productWeight.getProduct());
                    productStock.setWeight(0);
                    productStock.setPrice(0);
                }
                productStock.setWeight(productStock.getWeight()-(productWeight.getWeight()*orderItem.getQuantity()));
                productStockRepository.save(productStock);
            }
        }
    }

    public void saveMovement(ProductStockMovement movement) {
        movement.setDateOfOperation(LocalDateTime.now());
        productStockMovementRepository.save(movement);
        ProductStock productStock = productStockRepository.findByProductId(movement.getProduct().getId());
        if (movement.isTypeOfOperation()){
            productStock.setWeight(productStock.getWeight()+ movement.getWeight());
            productStock.setPrice(movement.getPrice());
        }else {
            productStock.setWeight(productStock.getWeight()- movement.getWeight());
        }
        productStockRepository.save(productStock);
    }
}
