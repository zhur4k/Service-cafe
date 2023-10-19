package com.yulkost.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CashRegisterController {

    @GetMapping("/cash_register")
    public String CashRegister(Model model){
        return "CashRegister";
    }

}
