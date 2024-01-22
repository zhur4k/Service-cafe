package com.yulkost.service.service;

import com.yulkost.service.model.*;
import com.yulkost.service.repository.ProductStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            for (ProductWeight productWeight :
                    orderItem.getItems().getProductsWeight()) {
                saveProductStock(productWeight, orderItem, orderItem.getQuantity());
            }
            itemWriteOff(orderItem.getItems().getChildItems(), orderItem, orderItem.getQuantity());
        }
    }
    private void itemWriteOff(List<ItemsInItem> childItems, OrderItems orderItem,  int parentQuantity) {
        if (childItems==null){
            return;
        }
        for (ItemsInItem item:
             childItems) {
            int quantityOfItems = parentQuantity * item.getQuantity();
            for (ProductWeight productWeight :
                    item.getItem().getProductsWeight()) {
                saveProductStock(productWeight, orderItem,quantityOfItems);
            }
            itemWriteOff(item.getItem().getChildItems(), orderItem, quantityOfItems);
            quantityOfItems=0;
        }

    }
    private void saveProductStock(ProductWeight productWeight,OrderItems orderItem,int quantity){
        ProductStock productStock = productStockRepository.findByProductId(productWeight.getProduct().getId());
        productStock.setWeight(productStock.getWeight()-(productWeight.getWeight()*quantity));
        productStockMovementService.saveMovementOrderItem(orderItem,new ProductStock(productStock),productWeight,quantity);
        productStockRepository.save(productStock);
    }
}
