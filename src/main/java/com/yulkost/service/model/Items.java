package com.yulkost.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
    private Boolean view;
    private int price;
    private int unitPrice;
    private String img;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private Units unit;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "categories_id", referencedColumnName = "id")
    private Categories categories;
    @OneToMany
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private List<ProductWeight> productsWeight = new ArrayList<>();
    public int getMarkup() {
        int priceOfAllProducts= 0;
        for (ProductWeight productWeight :
                this.getProductsWeight()) {
            priceOfAllProducts += productWeight.getProduct().getProductStock().getPrice()*productWeight.getWeight()/1000;
        }
        try {
            return (this.price-priceOfAllProducts)/priceOfAllProducts*100;
        }catch (ArithmeticException e)
        {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public String getPriceToPage() {
        double pr = (double) price;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.00",symbols).format(pr/100);
    }
    public void setPriceToPage(String price) {
        this.price = (int)(Double.parseDouble(price)*100);;
    }
    public String getUnitPriceToPage() {
        double pr = (double) unitPrice;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.000",symbols).format(pr/1000);
    }
    public void setUnitPriceToPage(String price) {
        this.unitPrice = (int)(Double.parseDouble(price)*1000);;
    }
}
