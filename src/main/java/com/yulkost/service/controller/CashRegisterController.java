package com.yulkost.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CashRegisterController {

    @GetMapping("/")
    public String CashRegister(){
        return "cashRegisterCafe";
    }
}
