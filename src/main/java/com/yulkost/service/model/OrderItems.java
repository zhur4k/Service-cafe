package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    @OneToOne()
    @JoinColumn(name = "order_item_id", referencedColumnName = "id")
    private Items item;
}
