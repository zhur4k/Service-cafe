package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ProductWeight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int weight;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Products product;
    @ManyToOne
    Items item;
}
