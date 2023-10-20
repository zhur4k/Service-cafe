package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "unix_time")
    private long unixTime;
//        @OneToMany(cascade = CascadeType.ALL)
//        @JoinColumn(name = "invoice_product_id", referencedColumnName = "id")
//        private List<InvoiceProduct> productsInInvoice = new ArrayList<>();

    }
