package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "items_id", referencedColumnName = "id")
    private Items items;
}
