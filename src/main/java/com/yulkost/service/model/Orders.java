package com.yulkost.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cashPaid;
    private int cashLessPaid;
    private int establishmentPaid;
    private int sumOfChange;
    private int numberOfTable;
    private LocalDateTime date;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private List<OrderItems> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shift_id", referencedColumnName = "id")
    private Shift shift;
    public String getCashPaidToPage(){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.00",symbols).format(((float)this.cashPaid)/100);
    }
    public String getCashLessPaidToPage(){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.00",symbols).format(((float)this.cashLessPaid)/100);
    }
}
