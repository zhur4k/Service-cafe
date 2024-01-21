package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="collections")
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime   dateOfOperation;
    private Boolean typeOfOperation;
    private Integer sumOfOperation;

    @ManyToOne
    Shift shift;
}
