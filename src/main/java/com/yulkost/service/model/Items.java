package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 13)
    private String code;
    private String nameOfItems;
    private Boolean view;
    private int price;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private Units unit;

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
    public void setPriceToPage(String price) {
        this.price = (int)(Double.parseDouble(price)*100);;
    }
}
