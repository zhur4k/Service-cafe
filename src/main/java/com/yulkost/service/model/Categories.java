package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoriesName;
    @OneToMany(mappedBy = "categories")
    private List<Items> items;
}
