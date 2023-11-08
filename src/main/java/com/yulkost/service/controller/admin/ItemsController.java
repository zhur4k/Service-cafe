package com.yulkost.service.controller.admin;

import com.yulkost.service.dto.ItemsEditDto;
import com.yulkost.service.dto.UserEditDto;
import com.yulkost.service.model.Items;
import com.yulkost.service.model.User;
import com.yulkost.service.service.CategoriesService;
import com.yulkost.service.service.ItemsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ItemsController {

    public ItemsService itemsService;
    private CategoriesService categoriesService;

    public ItemsController(ItemsService itemsService, CategoriesService categoriesService) {
        this.itemsService = itemsService;
        this.categoriesService = categoriesService;
    }

    @GetMapping("/items")
        public String Items(Model model){
        List<Items> items = new ArrayList<>();
        itemsService.findAll().iterator().forEachRemaining(items::add);
        model.addAttribute("form", new ItemsEditDto(items));
        model.addAttribute("category", categoriesService.findAll());
        return "adminItems";
        }

    @PostMapping("/items")
    public String ItemsEdit(Model model ,@ModelAttribute ItemsEditDto form) {
        itemsService.saveAll(form.getItems());
        return "redirect:/admin/items"; }

    @GetMapping("/items/add")
    public String ItemsAdd(Model model) {
        model.addAttribute("category", categoriesService.findAll());
        return "adminItemAdd"; }

    @PostMapping("/items/add")
    public String ItemsAdd(Model model , Items item) {
        itemsService.save(item);
        return "redirect:/admin/items"; }
}
