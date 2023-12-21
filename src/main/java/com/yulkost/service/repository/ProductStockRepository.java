package com.yulkost.service.repository;

import com.yulkost.service.model.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock,Long> {
    ProductStock findByProductId(Long id);
}
