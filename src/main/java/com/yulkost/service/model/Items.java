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
    private String uniqueCode;
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
    @OneToMany
    @JoinColumn(name = "parent_item_id", referencedColumnName = "id")
    private List<ItemsInItem> childItems = new ArrayList<>();
    @PrePersist
    @PreUpdate
    private void generateUniqueCode() {
        // Генерация уникального значения для code
        this.uniqueCode = generateUniqueCodeValue();
    }
    private String generateUniqueCodeValue() {
        // Ваша логика генерации уникального значения
        // В этом примере, мы используем текущее время в миллисекундах
        Long currentTime = System.currentTimeMillis();

        // Преобразуем в строку и обрезаем до 13 символов
        String codeValue = currentTime.toString();
        return codeValue.substring(0, Math.min(codeValue.length(), 13));
    }
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
