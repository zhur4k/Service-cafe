package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 13)
    private String code;
    private String name;
    private int price;
    @OneToMany()
    @JoinColumn(name = "product_weight_id", referencedColumnName = "id")
    private List<ProductWeight> productsWeight = new ArrayList<>();
}
