package com.yulkost.service.controller.admin;

import com.yulkost.service.dto.CategoriesEditDto;
import com.yulkost.service.model.Categories;
import com.yulkost.service.service.CategoriesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class CategoriesController {

    public CategoriesService categoriesService;

    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping
        public String Categories(Model model){
        List<Categories> category = new ArrayList<>();
        categoriesService.findAllCategories().iterator().forEachRemaining(category::add);
        model.addAttribute("form", new CategoriesEditDto(category));
        model.addAttribute("category", categoriesService.findAllCategories());

        return "adminCategories";
        }

    @PostMapping
    public String CategoriesEdit(@ModelAttribute CategoriesEditDto form) {
        categoriesService.saveAll(form.getCategory());
        return "redirect:/admin/categories"; }

    @GetMapping("/add")
    public String CategoriesAdd(Model model) {
        model.addAttribute("category", categoriesService.findAllCategories());
        return "adminCategoriesAdd"; }

    @PostMapping("/add")
    public String CategoriesAdd(Categories categories) {
        categoriesService.save(categories);
        return "redirect:/admin/categories"; }
    @GetMapping("/delete/{id}")
    public String CategoriesDelete(@PathVariable Long id) {
        try{
            categoriesService.deleteById(id);
        }catch (Exception e){
        }
        return "redirect:/admin/categories"; }
}
