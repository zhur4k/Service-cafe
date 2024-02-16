package com.yulkost.service.controller;

import com.yulkost.service.dto.CategoriesDtoToPage;
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
    public ResponseEntity<?> getCategory() {
        try {
            List<CategoriesDtoToPage> categories = categoriesService.findAll();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/getItemsToPage")
    public ResponseEntity<?> getItemsToPage() {
        try {
            return ResponseEntity.ok(itemsService.findAllList());
        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getOpenShift")
    public ResponseEntity<?> getOpenShift() {
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
    public ResponseEntity<?> closeShift(@AuthenticationPrincipal User user) {
        try {
            cashRegisterRestService.sendZReport();
            shiftService.closeShift();
            return ResponseEntity.ok("Success Shift was closed");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
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
    public ResponseEntity<?> SubmitOrder(@RequestBody Orders order) {
        order.setShift(shiftService.getOpenShift());
        Orders orderToSave= ordersService.OrderFromPageToOrders(order);
        if(orderToSave.getEstablishmentPaid()>0){
            ordersService.save(orderToSave);
            yulkostTelegramBotService.SendOrderToUser(orderToSave);
            return ResponseEntity.status(HttpStatus.OK).body("Успешно");
        }else {
            try {
                cashRegisterRestService.sendFCheck(orderToSave);
                ordersService.save(orderToSave);
                yulkostTelegramBotService.SendOrderToUser(orderToSave);
                return ResponseEntity.status(HttpStatus.OK).body("Успешно");
            }catch (RuntimeException e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }
    @PostMapping("/collectionMove")
    public ResponseEntity<?> CollectionMove(@RequestBody Collection collection) {
        try {
            collection.setShift(shiftService.getOpenShift());
            cashRegisterRestService.sendIOCheck(collection);
            collectionService.save(collection);
            return ResponseEntity.status(HttpStatus.OK).body("Успешно");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/addUserToShift")
    public void AddUserToShift(@RequestBody String login) {
        shiftService.addUser(shiftService.getOpenShift(), login);
    }
    @GetMapping("/getXReport")
    public ResponseEntity<String> getXReport() {
        try {
            cashRegisterRestService.sendXReport();
            return ResponseEntity.ok("X-отчёт успешно отправлен");

        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/getListOfUsers")
    public ResponseEntity<String> getListOfUsers() {
        return ResponseEntity.ok(shiftService.getListOfUsers());
    }
}
