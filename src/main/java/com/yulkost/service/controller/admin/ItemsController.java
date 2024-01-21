package com.yulkost.service.controller.admin;

import com.yulkost.service.dto.ItemsEditDto;
import com.yulkost.service.dto.ItemsInItemEditDto;
import com.yulkost.service.dto.ProductWeightEditDto;
import com.yulkost.service.model.Items;
import com.yulkost.service.model.ItemsInItem;
import com.yulkost.service.model.ProductWeight;
import com.yulkost.service.model.Products;
import com.yulkost.service.repository.ItemsInItemRepository;
import com.yulkost.service.repository.ProductWeightRepository;
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
    private ProductWeightRepository productWeightRepository;
    private ItemsInItemRepository itemsInItemRepository;
    private ImageService imageService;
    private ItemsInItemService itemsInItemService;
    @Autowired
    public void setItemsInItemService(ItemsInItemService itemsInItemService) {
        this.itemsInItemService = itemsInItemService;
    }

    @Autowired
    public void setItemsInItemRepository(ItemsInItemRepository itemsInItemRepository) {
        this.itemsInItemRepository = itemsInItemRepository;
    }

    @Autowired
    public void setProductWeightRepository(ProductWeightRepository productWeightRepository) {
        this.productWeightRepository = productWeightRepository;
    }

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

        model.addAttribute("itemsToPage", itemsService.findAll());
        List<ItemsInItem> itemsInItem = new ArrayList<>();
        items.getChildItems().iterator().forEachRemaining(itemsInItem::add);
        model.addAttribute("form2", new ItemsInItemEditDto(itemsInItem));
        return "adminItemChange"; }

    @PostMapping("/change/product_weight")
    public String ItemChange(@ModelAttribute ProductWeightEditDto form) {
        Long id = form.getProductWeights().get(0).getItem().getId();
        productWeightService.saveAll(form.getProductWeights());
        itemsService.setChangeTime(id);
        return "redirect:/admin/items/"+id; }

    @PostMapping("{id}/add_product_weight")
    public String ItemChange(@PathVariable Long id,String weightAdd,Long productAdd) {
        Products product = productsService.findById(productAdd);
        Items item = itemsService.findById(id);
        ProductWeight productWeight = productWeightRepository.findByProductAndItem(product, item);
        if (productWeight == null) {
            productWeight = new ProductWeight();
            productWeight.setWeightToPage(weightAdd);
            if (productWeight.getWeight() <= 0) {
                return "redirect:/admin/items/" + id;
            }
            productWeight.setProduct(product);
            productWeight.setItem(item);
        } else {
            productWeight.addWeight(weightAdd);
            if (productWeight.getWeight() <= 0) {
                return "redirect:/admin/items/" + id;
            }
        }
        productWeightService.save(productWeight);
        itemsService.setChangeTime(item.getId());
        return "redirect:/admin/items/" + id;
    }
    @PostMapping("/change/items")
    public String ItemChangeItems(@ModelAttribute ItemsInItemEditDto form) {
        Long id = form.getItemsInItem().get(0).getParentItem().getId();
        itemsInItemService.saveAll(form.getItemsInItem());
        itemsService.setChangeTime(id);
        return "redirect:/admin/items/"+id; }

    @PostMapping("{id}/add_item")
    public String ItemChangeItem(@PathVariable Long id,ItemsInItem itemsInItem) {
        if(itemsInItem.getQuantity() <=0){
            return "redirect:/admin/items/"+id;
        }
        Items item = itemsService.findById(id);
        itemsInItem.setParentItem(item);
        ItemsInItem itemsInItem1 = itemsInItemRepository.findByItemAndParentItem(itemsInItem.getItem(),itemsInItem.getParentItem());
        if(itemsInItem1==null){
            itemsInItem1=itemsInItem;
        }else{
            itemsInItem1.setQuantity(itemsInItem.getQuantity()+itemsInItem1.getQuantity());
        }
        itemsInItemRepository.save(itemsInItem1);
        itemsService.setChangeTime(item.getId());
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
            if(!image.isEmpty()){
                item.setImg(imageService.saveImg(image));
            }
            itemsService.save(item);
            return "redirect:/admin/items";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         }
}
