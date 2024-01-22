package com.yulkost.service.service;

import com.yulkost.service.model.ItemsInItem;
import com.yulkost.service.repository.ItemsInItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class ItemsInItemService {
    private ItemsInItemRepository itemsInItemRepository;
    @Autowired
    public void setItemsInItemRepository(ItemsInItemRepository itemsInItemRepository) {
        this.itemsInItemRepository = itemsInItemRepository;
    }
    public void saveAll(List<ItemsInItem> itemsInItem) {
        if(!itemsInItem.isEmpty()){
            removeErrorsWithItemInItem(itemsInItem);
            foldSameItems(itemsInItem);
            checkZero(itemsInItem);

            if(!itemsInItem.isEmpty()) {
                itemsInItemRepository.saveAll(itemsInItem);
            }
        }
    }
    private List<ItemsInItem> foldSameItems(List<ItemsInItem> itemsInItem) {
            for (ItemsInItem itemInItem :
                    itemsInItem) {
                for (ItemsInItem itemsInItem1 :
                        itemsInItem) {
                    if (itemInItem != itemsInItem1 && Objects.equals(itemsInItem1.getItem().getId(), itemInItem.getItem().getId())) {
                        itemInItem.setQuantity(itemInItem.getQuantity() + itemsInItem1.getQuantity());
                        itemsInItem1.setQuantity(0);
                    }
                }
            }
        return itemsInItem;
    }

    private List<ItemsInItem> removeErrorsWithItemInItem(List<ItemsInItem> itemsInItem){
        Iterator<ItemsInItem> iterator = itemsInItem.iterator();
        while (iterator.hasNext()) {
            ItemsInItem itemInItem = iterator.next();

            if(Objects.equals(itemInItem.getItem()
                    .getId(), itemInItem.getParentItem().getId())){
                iterator.remove();
                break;
            }
            for (ItemsInItem itemInItem1:
                    itemInItem.getItem().getChildItems()) {
                if(Objects.equals(itemInItem1.getItem()
                        .getId(), itemInItem.getParentItem().getId())){
                    iterator.remove();
                    break;
                }
            }
        }
        return itemsInItem;
    }
    private List<ItemsInItem> checkZero(List<ItemsInItem> itemsInItem){
        Iterator<ItemsInItem> iterator = itemsInItem.iterator();
        while (iterator.hasNext()) {
            ItemsInItem itemInItem = iterator.next();
            if (itemInItem.getQuantity()<= 0) {
                iterator.remove();
                itemsInItemRepository.delete(itemInItem);
            }
        }
        return itemsInItem;
    }
}
