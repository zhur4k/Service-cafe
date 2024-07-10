package com.yulkost.service.repository;

import com.yulkost.service.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Items,Long> {
    Items findByNameOfItemsAndPrice(String nameOfItems, int price);
    List<Items> findAllByView(boolean view);
}
