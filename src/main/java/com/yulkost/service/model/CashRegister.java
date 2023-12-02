package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cash_register")
public class CashRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cashAmount;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Orders order;

    @OneToOne
    @JoinColumn(name = "colection_id", referencedColumnName = "id")
    private Collection collection;
}
