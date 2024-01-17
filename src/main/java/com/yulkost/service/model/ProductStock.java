package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

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
        double pr = (double) this.price;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.00",symbols).format(pr/100);
    }
    public void setPriceToPage(String price) {
        this.price = (int)(Double.parseDouble(price)*100);;
    }
    public String getWeightToPage() {
        double pr = (double) this.weight;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.000",symbols).format(pr/1000.0);
    }
    public void setWeightToPage(String price) {
        this.weight = (int)(Double.parseDouble(price)*1000.0);
    }
}
