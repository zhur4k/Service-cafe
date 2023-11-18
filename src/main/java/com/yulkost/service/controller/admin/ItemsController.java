package com.yulkost.service.controller.admin;

import com.yulkost.service.dto.ItemsEditDto;
import com.yulkost.service.model.Items;
import com.yulkost.service.model.Products;
import com.yulkost.service.service.CategoriesService;
import com.yulkost.service.service.ItemsService;
import com.yulkost.service.service.ProductsService;
import com.yulkost.service.service.UnitsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ItemsController {

   private final ItemsService itemsService;
    private final CategoriesService categoriesService;
    private final UnitsService unitsService;
    private final ProductsService productsService;

    public ItemsController(ItemsService itemsService, CategoriesService categoriesService, UnitsService unitsService, ProductsService productsService) {
        this.itemsService = itemsService;
        this.categoriesService = categoriesService;
        this.unitsService = unitsService;
        this.productsService = productsService;
    }

    @GetMapping("/items")
        public String Items(Model model){
        List<Items> items = new ArrayList<>();
        itemsService.findAll().iterator().forEachRemaining(items::add);
        model.addAttribute("form", new ItemsEditDto(items));
        model.addAttribute("category", categoriesService.findAll());
        model.addAttribute("units", unitsService.findAll());
        return "adminItems";
        }

    @PostMapping("/items")
    public String ItemsEdit(Model model ,@ModelAttribute ItemsEditDto form) {
        itemsService.saveAll(form.getItems());
        return "redirect:/admin/items"; }

    @GetMapping("/items/{id}")
    public String ItemsAdd(@PathVariable Long id, Model model) {
        model.addAttribute("products", productsService.findAll());
        model.addAttribute("items", itemsService.findById(id));
        return "adminItemChange"; }

    @PostMapping("/items/change")
    public String ItemChange(Model model , Items item) {
        itemsService.save(item);
        return "redirect:/admin/items"; }
    @GetMapping("/items/add")
    public String ItemsAdd(Model model) {
        model.addAttribute("category", categoriesService.findAll());
        model.addAttribute("units", unitsService.findAll());
        return "adminItemAdd"; }

    @PostMapping("/items/add")
    public String ItemsAdd(Model model , Items item) {
        itemsService.save(item);
        return "redirect:/admin/items"; }
}
