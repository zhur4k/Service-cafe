package com.yulkost.service.controller.admin;

import com.yulkost.service.dto.ProductsEditDto;
import com.yulkost.service.model.Products;
import com.yulkost.service.service.ProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class ProductsController {
    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public String Products(Model model){
        List<Products> products = new ArrayList<>();
        productsService.findAll().iterator().forEachRemaining(products::add);
        model.addAttribute("form", new ProductsEditDto(products));
        return "adminProducts";
    }

    @PostMapping
    public String ProductsEdit(@ModelAttribute ProductsEditDto form) {
        productsService.saveAll(form.getProducts());
        return "redirect:/admin/products"; }

    @GetMapping("/add")
    public String ProductsAdd() {
        return "adminProductsAdd"; }

    @PostMapping("/add")
    public String ProductsAdd(Products products) {
        productsService.save(products);
        return "redirect:/admin/products"; }
}
