package com.yulkost.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startDate;
    private boolean stateOfShift;
    private LocalDateTime endDate;

    @JsonIgnore
    @ManyToOne
    private User user;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "orders_id", referencedColumnName = "id")
    private List<Orders> orders = new ArrayList<>();

    public Shift() {
        this.startDate = LocalDateTime.now();
        this.stateOfShift = true;
    }
}
