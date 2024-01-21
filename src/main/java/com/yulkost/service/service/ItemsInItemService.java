package com.yulkost.service.service;

import com.yulkost.service.model.ItemsInItem;
import com.yulkost.service.model.ProductWeight;
import com.yulkost.service.repository.ItemsInItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ItemsInItemService {
    private ItemsInItemRepository itemsInItemRepository;
    @Autowired
    public void setItemsInItemRepository(ItemsInItemRepository itemsInItemRepository) {
        this.itemsInItemRepository = itemsInItemRepository;
    }

    public void saveAll(List<ItemsInItem> itemsInItem) {
        Iterator<ItemsInItem> iterator = itemsInItem.iterator();
        while (iterator.hasNext()) {
            ItemsInItem itemInItem1 = iterator.next();
            if (itemInItem1.getQuantity()<= 0) {
                iterator.remove();
                itemsInItemRepository.delete(itemInItem1);
            }
        }
        if(!itemsInItem.isEmpty()){
            itemsInItemRepository.saveAll(itemsInItem);
        }
    }
}
