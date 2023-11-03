package com.yulkost.service.dto;



import com.yulkost.service.model.Items;

import java.util.ArrayList;
import java.util.List;

public class ItemsEditDto {
    private final List<Items> items;

    public ItemsEditDto(){
        this.items = new ArrayList<>();
    }
    public ItemsEditDto(List<Items> items) {
        this.items = items;
    }

    public List<Items> getItems() {
        return items;
    }

}
