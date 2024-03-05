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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Products product;
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    Items item;
    public String getSumToPage() {
        return new DecimalFormat("0.00").format(((float)this.weight)/1000 *((float)product.getProductStock().getPrice())/100);
    }
    public String getPriceToPage(){
        return product.getProductStock().getPriceToPage();
    }
    public String getWeightToPage(){
        double pr = (double) this.weight;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.000",symbols).format(pr/1000);
    }

    public void setWeightToPage(String weight){
        this.weight = (int)(Double.parseDouble(weight)*1000);
    }
    public void addWeight(String weight){
        this.weight += (int)(Double.parseDouble(weight)*1000);
    }

}
