package com.yulkost.service.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoriesName;
    @ToString.Exclude
    @OneToMany(mappedBy = "categories")
    private List<Items> items = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_category_id", referencedColumnName = "id")
    @JsonIgnore
    private Categories parentCategory;

    @OneToMany(mappedBy = "parentCategory",fetch = FetchType.EAGER)
    private List<Categories> childCategories = new ArrayList<>();
}
