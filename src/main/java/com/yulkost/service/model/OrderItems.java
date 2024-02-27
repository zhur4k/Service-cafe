package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;

@Data
@Entity
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private String uniqueCode;

    private String nameOfItems;
    private Boolean typeOfItem;
    private int price;
    private int unitPrice;
    private LocalDateTime dateOfItemChange;
    private String unit;
    private String category;
    private int discount;

    private Long item;

    public String getSumToPage(){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.00",symbols).format(((double)price)/100*((double)this.getQuantity()));
    }
    public String getQuantityUnitPriceToPage(){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.000",symbols).format(this.getQuantity()*((double)unitPrice)/1000);
    }
    public String getPriceToPage() {
        double pr = (double) price;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.00",symbols).format(pr/100);
    }
    public String getUnitPriceToPage() {
        double pr = (double) unitPrice;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.000",symbols).format(pr/1000);
    }
}
