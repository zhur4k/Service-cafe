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
    private String nameOfItems;
    private int price;

    @ManyToOne
    @JoinColumn(name = "categories_id", referencedColumnName = "id")
    private Categories categories;

    @OneToMany
    @JoinColumn(name = "product_weight_id", referencedColumnName = "id")
    private List<ProductWeight> productsWeight = new ArrayList<>();

    public String getPriceToPage() {
        double pr = (double) price;
        return Double.toString(pr/100);
    }
}
