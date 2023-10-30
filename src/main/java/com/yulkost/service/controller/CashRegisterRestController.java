package com.yulkost.service.controller;

import com.yulkost.service.model.Orders;
import com.yulkost.service.service.CashRegisterService;
import com.yulkost.service.service.OrdersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CashRegisterRestController {

    public CashRegisterService cashRegisterService;
    public OrdersService ordersService;

    public CashRegisterRestController(CashRegisterService cashRegisterService, OrdersService ordersService) {
        this.cashRegisterService = cashRegisterService;
        this.ordersService = ordersService;
    }

    @PostMapping("/submitOrder")
    public String SubmitOrder(@RequestBody Orders order) {

        if(cashRegisterService.SendFCheck(order)){
            ordersService.save(order);
            return "true";
        }
        return "false";
    }
}
