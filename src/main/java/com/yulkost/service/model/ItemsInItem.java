package com.yulkost.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

@Data
@Entity
public class ItemsInItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @JsonIgnore
    @ManyToOne
    private Items item;
    @JsonIgnore
    @ManyToOne
    private Items parentItem;

    public String getSumToPage() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.00",symbols).format(item.sumOfAllProducts()/100*quantity);
    }
}
