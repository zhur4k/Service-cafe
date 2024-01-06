package com.yulkost.service.controller.admin;

import com.yulkost.service.dto.ProductStockEditDto;
import com.yulkost.service.model.ProductStock;
import com.yulkost.service.repository.ProductStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/stock")
public class ProductStockController {
    private ProductStockRepository productStockRepository;
    @Autowired
    public void setProductStockRepository(ProductStockRepository productStockRepository) {
        this.productStockRepository = productStockRepository;
    }

    @GetMapping
    public String ProductStock(Model model){
        List<ProductStock> productStocks = new ArrayList<>();
        productStockRepository.findAll().iterator().forEachRemaining(productStocks::add);
        model.addAttribute("form", new ProductStockEditDto(productStocks));
        return "adminProductStock";
    }
    @PostMapping
    public String ProductStockEdit(@ModelAttribute ProductStockEditDto form) {
        productStockRepository.saveAll(form.getProductStock());
        return "redirect:/admin/stock"; }
}
