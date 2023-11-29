package com.yulkost.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yulkost.service.model.Categories;
import com.yulkost.service.service.CategoriesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
