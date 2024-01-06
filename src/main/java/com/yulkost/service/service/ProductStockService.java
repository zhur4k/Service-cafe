package com.yulkost.service.service;

import com.yulkost.service.model.OrderItems;
import com.yulkost.service.model.Orders;
import com.yulkost.service.model.ProductStock;
import com.yulkost.service.model.ProductWeight;
import com.yulkost.service.repository.ProductStockRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductStockService {
    ProductStockRepository productStockRepository;

    public ProductStockService(ProductStockRepository productStockRepository) {
        this.productStockRepository = productStockRepository;
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
}
