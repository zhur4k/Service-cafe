package com.yulkost.service.controller;

import com.yulkost.service.service.ItemsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CashRegisterController {
    public ItemsService itemsService;

    public CashRegisterController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @GetMapping("/cash_register")
    public String CashRegister(Model model){
        model.addAttribute("category" ,itemsService.findAllCategories());
        return "cashRegisterCafe";
    }
    @GetMapping("/cash_register2")
    public String CashRegister2(Model model){
        return "cashRegister";
    }
}
