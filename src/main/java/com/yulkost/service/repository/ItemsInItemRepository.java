package com.yulkost.service.repository;

import com.yulkost.service.model.ItemsInItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsInItemRepository extends JpaRepository<ItemsInItem,Long> {


    List<ItemsInItem> findAllByItemId(Long id);
}
