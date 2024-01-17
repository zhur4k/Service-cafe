package com.yulkost.service.service;

import com.yulkost.service.model.*;
import com.yulkost.service.repository.ProductStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductStockService {
    private ProductStockRepository productStockRepository;
    private ProductStockMovementService productStockMovementService;
    @Autowired
    public void setProductStockRepository(ProductStockRepository productStockRepository) {
        this.productStockRepository = productStockRepository;
    }
    @Autowired
    public void setProductStockMovementService(ProductStockMovementService productStockMovementService) {
        this.productStockMovementService = productStockMovementService;
    }

    public void writeOffProductFromStockAndSaveToStockMovement(Orders order){
        for (OrderItems orderItem : order.getOrderItems()) {
            for (ProductWeight productWeight : orderItem.getItems().getProductsWeight()) {
                ProductStock productStock = productStockRepository.findByProductId(productWeight.getProduct().getId());
                productStockMovementService.saveMovementOrderItem(orderItem,productStock,productWeight);
                productStock.setWeight(productStock.getWeight()-(productWeight.getWeight()*orderItem.getQuantity()));
                productStockRepository.save(productStock);
            }
        }
    }
}
