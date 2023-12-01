package com.yulkost.service.controller;

import com.yulkost.service.model.*;
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

    private ShiftService shiftService;

    public CashRegisterRestController(CashRegisterService cashRegisterService, OrdersService ordersService, YulkostTelegramBotService yulkostTelegramBotService, CategoriesService categoriesService, ItemsService itemsService, ShiftService shiftService) {
        this.cashRegisterService = cashRegisterService;
        this.ordersService = ordersService;
        this.yulkostTelegramBotService = yulkostTelegramBotService;
        this.categoriesService = categoriesService;
        this.itemsService = itemsService;
        this.shiftService = shiftService;
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

    @GetMapping("/getOpenShift")
    public ResponseEntity<Shift> getOpenShift(@AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.ok(shiftService.getOpenShift(user));
        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/openShift")
    public ResponseEntity<Shift> openShift(@AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.ok(shiftService.openShift(user));
        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/closeShift")
    public ResponseEntity<String> closeShift(@AuthenticationPrincipal User user) {
        try {
            if(cashRegisterService.sendZReport()){
                shiftService.closeShift(user);
                return ResponseEntity.ok("Success Shift was closed");
            }
            return null;
        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/submitOrder")
    public String SubmitOrder(@RequestBody Orders order,@AuthenticationPrincipal User user) {
        order.setShift(shiftService.openShift(user));
        Orders orderToSave= ordersService.OrderFromPageToOrders(order);
        if(cashRegisterService.sendFCheck(orderToSave)){
            ordersService.save(orderToSave);
            yulkostTelegramBotService.SendOrderToUser(ordersService.save(orderToSave));
            return "true";
        }
        return "false";
    }
    @GetMapping("/getXReport")
    public ResponseEntity<String> getXReport() {
        try {
            if(cashRegisterService.sendXReport())
                return ResponseEntity.ok("Success send X-Report");

            throw new Exception();

        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
