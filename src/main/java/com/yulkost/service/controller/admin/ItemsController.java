package com.yulkost.service.controller.admin;

import com.yulkost.service.dto.ItemsEditDto;
import com.yulkost.service.dto.ProductWeightEditDto;
import com.yulkost.service.model.Items;
import com.yulkost.service.model.ProductWeight;
import com.yulkost.service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/items")
public class ItemsController {

   private final ItemsService itemsService;
    private final CategoriesService categoriesService;
    private final UnitsService unitsService;
    private final ProductsService productsService;
    private final ProductWeightService productWeightService;
    private ImageService imageService;

    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    public ItemsController(ItemsService itemsService, CategoriesService categoriesService, UnitsService unitsService, ProductsService productsService, ProductWeightService productWeightService) {
        this.itemsService = itemsService;
        this.categoriesService = categoriesService;
        this.unitsService = unitsService;
        this.productsService = productsService;
        this.productWeightService = productWeightService;
    }

    @GetMapping
    public String Items(Model model){
        List<Items> items = new ArrayList<>();
        itemsService.findAll().iterator().forEachRemaining(items::add);
        model.addAttribute("form", new ItemsEditDto(items));
        model.addAttribute("category", categoriesService.findAll());
        model.addAttribute("units", unitsService.findAll());
        return "adminItems";
        }

    @PostMapping
    public String ItemsEdit(@ModelAttribute ItemsEditDto form) {
        itemsService.saveAll(form.getItems());
        return "redirect:/admin/items"; }

    @GetMapping("/{id}")
    public String ItemsAdd(@PathVariable Long id, Model model) {
        model.addAttribute("products", productsService.findAll());
        Items items = itemsService.findById(id);
        model.addAttribute("items", items);
        List<ProductWeight> productWeights = new ArrayList<>();
        items.getProductsWeight().iterator().forEachRemaining(productWeights::add);
        model.addAttribute("form", new ProductWeightEditDto(productWeights));
        return "adminItemChange"; }

    @PostMapping("/change")
    public String ItemChange(@ModelAttribute ProductWeightEditDto form) {
        productWeightService.saveAll(form.getProductWeights());
        return "redirect:/admin/items"; }

    @PostMapping("{id}/addProduct")
    public String ItemChange(@PathVariable Long id,Integer weightAdd,Long productAdd) {
        ProductWeight productWeight = new ProductWeight();
        productWeight.setWeight(weightAdd);
        productWeight.setProduct(productsService.findById(productAdd));
        productWeight.setItem(itemsService.findById(id));
        productWeightService.save(productWeight);
        return "redirect:/admin/items/"+id;
    }
    @GetMapping("/add")
    public String ItemsAdd(Model model) {
        model.addAttribute("category", categoriesService.findAll());
        model.addAttribute("units", unitsService.findAll());
        return "adminItemAdd"; }

    @PostMapping("/add")
    public String ItemsAdd(Items item, @RequestPart MultipartFile image) {
        try {
            item.setImg(imageService.saveImg(image));
            itemsService.save(item);
            return "redirect:/admin/items";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         }
}
