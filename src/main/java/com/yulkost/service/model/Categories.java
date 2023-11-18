package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoriesName;
    @OneToMany(mappedBy = "categories")
    private List<Items> items = new ArrayList<>();

    public List<Items> getItemsToPage() {
       List<Items> items1 = new ArrayList<>();
        for (Items item :
                items) {
            if(item.getView()) items1.add(item);
        }
        return items1;
    }
}
