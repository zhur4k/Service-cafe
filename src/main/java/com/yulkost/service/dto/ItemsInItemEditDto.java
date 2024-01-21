package com.yulkost.service.dto;

import com.yulkost.service.model.ItemsInItem;

import java.util.ArrayList;
import java.util.List;

public class ItemsInItemEditDto {
    private final List<ItemsInItem> itemsInItem;

    public ItemsInItemEditDto(){
        this.itemsInItem = new ArrayList<>();
    }
    public ItemsInItemEditDto(List<ItemsInItem> itemsInItem) {
        this.itemsInItem = itemsInItem;
    }

    public List<ItemsInItem> getItemsInItem() {
        return itemsInItem;
    }

}
