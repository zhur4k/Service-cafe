package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private int price;
    private int markup;
    private String name;

    @ManyToOne
    @JoinColumn(name = "items_id", referencedColumnName = "id")
    private Items items;

    @OneToMany(mappedBy = "orderItems", cascade = CascadeType.ALL)
    private List<ProductStockMovement> productStockMovements = new ArrayList<>();
    @PrePersist
    @PreUpdate
    private void calculateMarkup() {
        //Установить наименование
        this.name= this.items.getNameOfItems();
        //Установить цену
        this.price= this.items.getPrice();
        //Установить наценку
        this.markup = this.items.getMarkup();
    }
}
