package com.yulkost.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
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
    private Boolean typeOfItem;

    private int price;
    private int unitPrice;
    private String img;
    private LocalDateTime dateOfChange;
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
        double priceOfAllProducts= 0;
        for (ProductWeight productWeight :
                this.getProductsWeight()) {
            priceOfAllProducts += ((double)productWeight.getProduct().getProductStock().getPrice()) *((double)productWeight.getWeight())/1000;
        }
        try {
            if (this.getProductsWeight().isEmpty()){
                return 0;
            }
            else{
                return (int)(((double)this.price-priceOfAllProducts)/priceOfAllProducts*100);
            }
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
