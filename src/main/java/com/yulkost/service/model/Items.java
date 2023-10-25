package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 13)
    private String code;
    private String name;
    private int price;
}
