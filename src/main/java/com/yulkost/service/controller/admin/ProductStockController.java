package com.yulkost.service.controller.admin;

import com.yulkost.service.model.ProductStockMovement;
import com.yulkost.service.repository.ProductStockMovementRepository;
import com.yulkost.service.repository.ProductStockRepository;
import com.yulkost.service.repository.ProductsRepository;
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
    private ProductStockService productStockService;
    private ProductStockMovementRepository productStockMovementRepository;
    @Autowired
    public void setProductStockRepository(ProductStockRepository productStockRepository) {
        this.productStockRepository = productStockRepository;
    }
    @Autowired
    public void setProductsRepository(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }
    @Autowired
    public void setProductStockService(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }
    @Autowired
    public void setProductStockMovementRepository(ProductStockMovementRepository productStockMovementRepository) {
        this.productStockMovementRepository = productStockMovementRepository;
    }

    @GetMapping
    public String ProductStock(Model model){
//        List<ProductStock> productStocks = new ArrayList<>();
//        productStockRepository.findAll().iterator().forEachRemaining(productStocks::add);
//        model.addAttribute("form", new ProductStockEditDto(productStocks));
          model.addAttribute("form", productStockRepository.findAll());
        return "adminProductStock";
    }
    @GetMapping("/movement")
    public String ProductStockMovement(Model model){
//        List<ProductStock> productStocks = new ArrayList<>();
//        productStockRepository.findAll().iterator().forEachRemaining(productStocks::add);
//        model.addAttribute("form", new ProductStockEditDto(productStocks));
        model.addAttribute("form", productStockMovementRepository.findAll());
        return "adminProductStockMovement";
    }
//    @PostMapping
//    public String ProductStockEdit(@ModelAttribute ProductStockEditDto form) {
//        productStockRepository.saveAll(form.getProductStock());
//        return "redirect:/admin/stock"; }
    @GetMapping("/add")
    public String ProductStockAdd(Model model){
        model.addAttribute("products", productsRepository.findAll());
    return "adminProductStockAdd";
}
    @PostMapping("/add")
    public String ProductStockAdd(@ModelAttribute ProductStockMovement movement) {
        movement.setTypeOfOperation(true);
        productStockService.saveMovement(movement);
        return "redirect:/admin/stock"; }
    @GetMapping("/care")
    public String ProductStockCare(Model model){
        model.addAttribute("products", productsRepository.findAll());
        return "adminProductStockCare";
    }
    @PostMapping("/care")
    public String ProductStockCare(@ModelAttribute ProductStockMovement movement) {
        movement.setTypeOfOperation(false);
        productStockService.saveMovement(movement);
        return "redirect:/admin/stock"; }
}
