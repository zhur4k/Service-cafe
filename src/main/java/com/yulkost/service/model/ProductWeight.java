package com.yulkost.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

@Data
@Entity
public class ProductWeight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int weight;
    @ToString.Exclude
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
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.00",symbols).format(pr/100);
    }
    public String getWeightToPage(){
        double pr = (double) this.weight/1000;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.000",symbols).format(pr/1000);
    }
    public void setWeightToPage(String weight){
        this.weight = (int)(Double.parseDouble(weight)*1000);
    }
}
