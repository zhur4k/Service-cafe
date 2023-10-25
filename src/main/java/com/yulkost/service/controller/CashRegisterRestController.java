package com.yulkost.service.controller;

import com.yulkost.service.service.CashRegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CashRegisterRestController {

    public CashRegisterService cashRegisterService;

    @PostMapping("/submitOrder")
    public String SubmitOrder() {
        return cashRegisterService.SendCheck();
    }
}
