package com.yulkost.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
        private Date date;
        private String nameOfCompany;
        private String ttn;
        private int withoutNDS;
        private int sumNDS;
        private int withNDS;
        private int sumMargin;
        private int priseProduct;
        private int sumOfAllNDS;


//        @OneToMany(cascade = CascadeType.ALL)
//        @JoinColumn(name = "invoice_product_id", referencedColumnName = "id")
//        private List<InvoiceProduct> productsInInvoice = new ArrayList<>();

    }
