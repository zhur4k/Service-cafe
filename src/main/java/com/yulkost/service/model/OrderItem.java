package com.yulkost.service.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class OrderItem {
    private String item;
    private double price;
    private int quantity;
}
