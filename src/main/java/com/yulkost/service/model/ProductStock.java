package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ProductStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int weight;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Products product;
}
