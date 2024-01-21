package com.yulkost.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ItemsInItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @JsonIgnore
    @ManyToOne
    private Items item;
    @JsonIgnore
    @ManyToOne
    private Items parentItem;
}
