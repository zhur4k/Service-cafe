package com.yulkost.service.controller.admin;

import com.yulkost.service.dto.CategoriesEditDto;
import com.yulkost.service.model.Categories;
import com.yulkost.service.service.CategoriesService;
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
public class CategoriesController {

    public CategoriesService categoriesService;

    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping("/categories")
        public String Categories(Model model){
        List<Categories> category = new ArrayList<>();
        categoriesService.findAll().iterator().forEachRemaining(category::add);
        model.addAttribute("form", new CategoriesEditDto(category));
        return "adminCategories";
        }

    @PostMapping("/categories")
    public String CategoriesEdit(Model model ,@ModelAttribute CategoriesEditDto form) {
        System.out.println(form.getCategory().get(0).getCategoriesName());
        categoriesService.saveAll(form.getCategory());
        return "redirect:/admin"; }

    @GetMapping("/categories/add")
    public String CategoriesAdd(Model model) {
        return "adminCategoriesAdd"; }

    @PostMapping("/categories/add")
    public String CategoriesAdd(Model model , Categories categories) {
        System.out.println(categories.getCategoriesName());
        categoriesService.save(categories);
        return "redirect:/admin"; }
}
