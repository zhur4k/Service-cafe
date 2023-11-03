package com.yulkost.service.controller;

import com.yulkost.service.service.CategoriesService;
import com.yulkost.service.service.ItemsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CashRegisterController {
    public CategoriesService categoriesService;

    public CashRegisterController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping("/")
    public String CashRegister(Model model){
        model.addAttribute("category" ,categoriesService.findAll());
        return "cashRegisterCafe";
    }
}
