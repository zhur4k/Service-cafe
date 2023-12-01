package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paymentMethod;
    private LocalDateTime date;
    @OneToMany
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private List<OrderItems> orderItems = new ArrayList<>();
    @ManyToOne
    private Shift shift;
    }
