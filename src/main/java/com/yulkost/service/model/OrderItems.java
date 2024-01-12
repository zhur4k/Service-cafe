package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Formula;

@Data
@Entity
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private int price;
    private int markup;
    @ManyToOne
    @JoinColumn(name = "items_id", referencedColumnName = "id")
    private Items items;
    @PrePersist
    @PreUpdate
    private void calculateMarkup() {
        //Установить цену
        this.price= this.items.getPrice();
        //Установить наценку
        this.markup = this.items.getMarkup();
    }
}
