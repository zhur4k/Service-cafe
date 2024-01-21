package com.yulkost.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ManyToMany
    @JoinTable(
            name = "shift_users",
            joinColumns = @JoinColumn(name = "shift_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users = new ArrayList<>();

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "shift_id", referencedColumnName = "id")
    private List<Orders> orders = new ArrayList<>();

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "shift_id", referencedColumnName = "id")
    private List<Collection> collections = new ArrayList<>();

   public Shift() {
        this.startDate = LocalDateTime.now();
        this.stateOfShift = true;
    }
}
