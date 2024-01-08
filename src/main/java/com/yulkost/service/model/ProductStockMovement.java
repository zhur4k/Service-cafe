package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class ProductStockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int weight;
    private int price;
    private String description;
    /**
     * true - adding false - care
     */
    private boolean typeOfOperation;
    private LocalDateTime dateOfOperation;

    @ManyToOne
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
