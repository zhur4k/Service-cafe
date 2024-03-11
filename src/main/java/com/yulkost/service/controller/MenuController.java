package com.yulkost.service.controller;

import com.yulkost.service.service.CategoriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
public class MenuController {
    private final CategoriesService categoriesService;

    public MenuController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping
    public String menu(){
        return "menu";
    }
    @GetMapping("/getCategoriesForMenu")
    public ResponseEntity<?> getCategories(){
        try {
            return ResponseEntity.ok(categoriesService.findAllForMenu());
        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
