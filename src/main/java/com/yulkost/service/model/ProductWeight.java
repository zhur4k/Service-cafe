package com.yulkost.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class ProductWeight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int weight;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Products product;
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    Items item;
    public String getPriceToPage(){
        double pr = (double) product.getProductStock().getPrice()*getWeight()/1000;
        return Double.toString(pr/100);
    }
}
