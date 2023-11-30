package com.yulkost.service.controller;

import com.yulkost.service.model.Categories;
import com.yulkost.service.model.Items;
import com.yulkost.service.model.Orders;
import com.yulkost.service.model.User;
import com.yulkost.service.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CashRegisterRestController {

    public CashRegisterService cashRegisterService;
    public OrdersService ordersService;
    public YulkostTelegramBotService yulkostTelegramBotService;
    public CategoriesService categoriesService;
    public ItemsService itemsService;

    public CashRegisterRestController(CashRegisterService cashRegisterService, OrdersService ordersService, YulkostTelegramBotService yulkostTelegramBotService, CategoriesService categoriesService, ItemsService itemsService) {
        this.cashRegisterService = cashRegisterService;
        this.ordersService = ordersService;
        this.yulkostTelegramBotService = yulkostTelegramBotService;
        this.categoriesService = categoriesService;
        this.itemsService = itemsService;
    }

    @GetMapping("/getCategory")
    public ResponseEntity<List<Categories>> getCategory() {
        try {
            List<Categories> categories = categoriesService.findAll();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/getItemsToPage")
    public ResponseEntity<List<Items>> getItemsToPage() {
        try {
            return ResponseEntity.ok(itemsService.findAllList());
        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/submitOrder")
    public String SubmitOrder(@RequestBody Orders order,@AuthenticationPrincipal User user) {
        order.setCashier(user.getName());
        Orders orderToSave= ordersService.OrderFromPageToOrders(order);

        if(cashRegisterService.sendFCheck(orderToSave)){
            ordersService.save(orderToSave);
            yulkostTelegramBotService.SendOrderToUser(user,ordersService.save(orderToSave));
            return "true";
        }
        return "false";
    }
    @GetMapping("/getXReport")
    public ResponseEntity<Void> getXReport() {
        try {
            if(cashRegisterService.sendXReport())
                throw new Exception();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/getZReport")
    public ResponseEntity<Void> getZReport() {
        try {
            if(cashRegisterService.sendZReport())
                throw new Exception();

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
