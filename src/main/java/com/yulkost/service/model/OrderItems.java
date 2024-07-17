package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;

    private String nameOfItems;
    private Boolean typeOfItem;
    private int price;
    private LocalDateTime dateOfItemChange;
    private String unit;
    private String category;
    private int discount;
    @OneToMany(mappedBy = "orderItems", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductStockMovement> productStockMovement;
    private Long item;

    public String getSumToPage(){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.00",symbols).format(((double)price)/100*((double)this.getQuantity()));
    }
    public String getPriceToPage() {
        double pr = price;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.00",symbols).format(pr/100);
    }
}
