package com.yulkost.service.controller.admin;

import com.yulkost.service.model.ProductStockMovement;
import com.yulkost.service.repository.ProductStockMovementRepository;
import com.yulkost.service.repository.ProductStockRepository;
import com.yulkost.service.repository.ProductsRepository;
import com.yulkost.service.service.ProductStockMovementService;
import com.yulkost.service.service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/stock")
public class ProductStockController {
    private ProductStockRepository productStockRepository;
    private ProductsRepository productsRepository;
    private ProductStockMovementRepository productStockMovementRepository;
    private ProductStockMovementService movementService;

    @Autowired
    public void setProductStockRepository(ProductStockRepository productStockRepository) {
        this.productStockRepository = productStockRepository;
    }
    @Autowired
    public void setProductsRepository(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }
    @Autowired
    public void setProductStockMovementRepository(ProductStockMovementRepository productStockMovementRepository) {
        this.productStockMovementRepository = productStockMovementRepository;
    }
    @Autowired
    public void setMovementService(ProductStockMovementService movementService) {
        this.movementService = movementService;
    }

    @GetMapping
    public String ProductStock(Model model){
          model.addAttribute("form", productStockRepository.findAll());
        return "adminProductStock";
    }
    @GetMapping("/movement")
    public String ProductStockMovement(Model model){
        model.addAttribute("form", productStockMovementRepository.findAll());
        return "adminProductStockMovement";
    }
    @GetMapping("/add")
    public String ProductStockAdd(Model model){
        model.addAttribute("products", productsRepository.findAll());
    return "adminProductStockAdd";
}
    @PostMapping("/add")
    public String ProductStockAdd(@ModelAttribute ProductStockMovement movement) {
        movementService.saveMovementAdd(movement);
        return "redirect:/admin/stock"; }
    @GetMapping("/care")
    public String ProductStockCare(Model model){
        model.addAttribute("products", productsRepository.findAll());
        return "adminProductStockCare";
    }
    @PostMapping("/care")
    public String ProductStockCare(@ModelAttribute ProductStockMovement movement) {
        movementService.saveMovementRemove(movement);
        return "redirect:/admin/stock"; }
}
