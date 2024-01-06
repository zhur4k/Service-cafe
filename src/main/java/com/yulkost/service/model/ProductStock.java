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
    private int price;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Products product;

    public String getPriceToPage() {
        double pr = (double) price;
        return Double.toString(pr/100);
    }
    public void setPriceToPage(String price) {
        this.price = (int)(Double.parseDouble(price)*100);;
    }
}
