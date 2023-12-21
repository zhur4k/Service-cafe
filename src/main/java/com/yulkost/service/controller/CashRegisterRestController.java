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

    private CashRegisterRestService cashRegisterRestService;
    private OrdersService ordersService;
    private YulkostTelegramBotService yulkostTelegramBotService;
    private CategoriesService categoriesService;
    private ItemsService itemsService;
    private CollectionService collectionService;
    private ShiftService shiftService;
    private CashRegisterService cashRegisterService;

    public CashRegisterRestController(CashRegisterRestService cashRegisterRestService, OrdersService ordersService, YulkostTelegramBotService yulkostTelegramBotService, CategoriesService categoriesService, ItemsService itemsService, CollectionService collectionService, ShiftService shiftService, CashRegisterService cashRegisterService) {
        this.cashRegisterRestService = cashRegisterRestService;
        this.ordersService = ordersService;
        this.yulkostTelegramBotService = yulkostTelegramBotService;
        this.categoriesService = categoriesService;
        this.itemsService = itemsService;
        this.collectionService = collectionService;
        this.shiftService = shiftService;
        this.cashRegisterService = cashRegisterService;
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
    public ResponseEntity<Shift> getOpenShift() {
        try {
            return ResponseEntity.ok(shiftService.getOpenShift());
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
            if(cashRegisterRestService.sendZReport()){
                shiftService.closeShift(user);
                return ResponseEntity.ok("Success Shift was closed");
            }
            return null;
        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/sumInCashRegister")
    public ResponseEntity<String> sumInCashRegister() {
        try {
                return ResponseEntity.ok(cashRegisterService.getSumInCashRegister());
        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/submitOrder")
    public void SubmitOrder(@RequestBody Orders order) {
        order.setShift(shiftService.getOpenShift());
        Orders orderToSave= ordersService.OrderFromPageToOrders(order);
        if(cashRegisterRestService.sendFCheck(orderToSave)){
            ordersService.save(orderToSave);
            yulkostTelegramBotService.SendOrderToUser(orderToSave);
        }
    }
    @PostMapping("/collectionMove")
    public void CollectionMove(@RequestBody Collection collection) {
        collection.setShift(shiftService.getOpenShift());
        collectionService.save(collection);
    }

    @PostMapping("/addUserToShift")
    public void AddUserToShift(@RequestBody String login) {
        shiftService.addUser(shiftService.getOpenShift(), login);
    }
    @GetMapping("/getXReport")
    public ResponseEntity<String> getXReport() {
        try {
            if(cashRegisterRestService.sendXReport())
                return ResponseEntity.ok("Success send X-Report");

            throw new Exception();

        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/getListOfUsers")
    public ResponseEntity<String> getListOfUsers() {
        return ResponseEntity.ok(shiftService.getListOfUsers());
    }
}
