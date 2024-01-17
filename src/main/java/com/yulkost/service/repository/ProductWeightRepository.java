package com.yulkost.service.repository;

import com.yulkost.service.model.Items;
import com.yulkost.service.model.ProductWeight;
import com.yulkost.service.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductWeightRepository extends JpaRepository<ProductWeight,Long> {
    List<ProductWeight> findByItemId(Long id);

    ProductWeight findByProductAndItem(Products product, Items item);
}
