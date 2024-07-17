package com.yulkost.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    private String nameOfItems;
    private Boolean view;
    private Boolean typeOfItem;
    private String productVolume;

    private int price;
    private String img;
    private LocalDateTime dateOfChange;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private Units unit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categories_id", referencedColumnName = "id")
    @JsonIgnoreProperties("items")
    private Categories categories;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private List<ProductWeight> productsWeight = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_item_id", referencedColumnName = "id")
    private List<ItemsInItem> childItems = new ArrayList<>();
    public int getMarkup() {
        try {

            double priceOfAllProducts= sumOfAllProducts();
            for (ItemsInItem itemsInItem :
                    childItems) {
                priceOfAllProducts += itemsInItem.getItem().sumOfAllProducts();

            }
            if(priceOfAllProducts==0){
                return 0;
            }
            return (int)(((double)this.price-priceOfAllProducts)/priceOfAllProducts*100);
        }catch (ArithmeticException e)
        {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public double sumOfAllProducts() {
        double priceOfAllProducts = 0;
        for (ProductWeight productWeight :
                this.getProductsWeight()) {
            priceOfAllProducts += ((double) productWeight.getProduct().getProductStock().getPrice()) * (((double) productWeight.getWeight()) / 1000);
        }
        return priceOfAllProducts;
    }
    public String priceOfAllProductsToPage() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.00",symbols).format(sumOfAllProducts()/100);
    }
    public String getPriceToPage() {
        double pr = price;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.00",symbols).format(pr/100);
    }
    public void setPriceToPage(String price) {
        this.price = (int)(Double.parseDouble(price)*100);
    }
}
