package com.yulkost.service.repository;

import com.yulkost.service.model.ProductStockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockMovementRepository extends JpaRepository<ProductStockMovement,Long> {
}
