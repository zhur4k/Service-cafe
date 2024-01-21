package com.yulkost.service.repository;

import com.yulkost.service.model.Items;
import com.yulkost.service.model.ItemsInItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ItemsInItemRepository extends JpaRepository<ItemsInItem,Long> {
    ItemsInItem findByItemAndParentItem(Items item, Items parentItem);
}
