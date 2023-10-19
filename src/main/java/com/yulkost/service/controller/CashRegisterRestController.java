package com.yulkost.service.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CashRegisterRestController {

    @PostMapping("/submitOrder")
    public String SubmitOrder(@RequestBody OrderData orderData){
        return "Заказ успешно сохранен!";
    }
    

}
