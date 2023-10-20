package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class Order {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(name = "unix_time")
//    private long unixTime;
private String paymentMethod;
    private List<OrderItem> items;
    private double total;

    }
