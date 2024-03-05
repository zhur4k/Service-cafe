package com.yulkost.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ToString.Exclude
    @JsonIgnore
    @OneToOne(fetch=FetchType.EAGER)
    ProductStock productStock;
}
