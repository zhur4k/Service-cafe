package com.yulkost.service.repository;

import com.yulkost.service.model.ProductWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductWeightRepository extends JpaRepository<ProductWeight,Long> {
}
