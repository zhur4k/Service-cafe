package com.yulkost.service.controller;

import com.yulkost.service.dto.CategoriesDtoToPage;
import com.yulkost.service.model.*;
import com.yulkost.service.repository.OrderItemsRepository;
import com.yulkost.service.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CashRegisterRestController {

    private final CashRegisterRestService cashRegisterRestService;
    private final OrdersService ordersService;
    private final YulkostTelegramBotService yulkostTelegramBotService;
    private final CategoriesService categoriesService;
    private final ItemsService itemsService;
    private final CollectionService collectionService;
    private final ShiftService shiftService;
    private final CashRegisterService cashRegisterService;
    private final OrderItemsRepository orderItemsRepository;
    public CashRegisterRestController(CashRegisterRestService cashRegisterRestService, OrdersService ordersService, YulkostTelegramBotService yulkostTelegramBotService, CategoriesService categoriesService, ItemsService itemsService, CollectionService collectionService, ShiftService shiftService, CashRegisterService cashRegisterService, OrderItemsRepository orderItemsRepository) {
        this.cashRegisterRestService = cashRegisterRestService;
        this.ordersService = ordersService;
        this.yulkostTelegramBotService = yulkostTelegramBotService;
        this.categoriesService = categoriesService;
        this.itemsService = itemsService;
        this.collectionService = collectionService;
        this.shiftService = shiftService;
        this.cashRegisterService = cashRegisterService;
        this.orderItemsRepository = orderItemsRepository;
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
    public ResponseEntity<?> closeShift() {
        try {
            Shift shift = shiftService.getOpenShift();
                cashRegisterRestService.sendZReport();
                shiftService.closeShift(shift);
                return ResponseEntity.ok("Success Shift was closed");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/sumInCashRegister")
    public ResponseEntity<String> sumInCashRegister() {
        try {
                shiftService.shiftIsOpen();
                return ResponseEntity.ok(cashRegisterService.getSumInCashRegister());
        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/submitOrder")
    public ResponseEntity<?> SubmitOrder(@RequestBody Orders order) {
            shiftService.shiftIsOpen();
            order.setShift(shiftService.getOpenShift());
            Orders order1= ordersService.OrderFromPageToOrders(order);
        try {
            if (order1.getEstablishmentPaid() <= 0) {
                OrderItems orderItem = orderItemsRepository.findOrderItemsWithMaxId();
                Long id = 0L;
                if(orderItem!=null){
                    id = orderItem.getId();
                }
                cashRegisterRestService.sendFCheck(order1,id);
            }
            ordersService.save(order1);
            yulkostTelegramBotService.SendOrderToUser(order1);
            return ResponseEntity.status(HttpStatus.OK).body("Успешно");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PostMapping("/collectionMove")
    public ResponseEntity<?> CollectionMove(@RequestBody Collection collection) {
        try {
            shiftService.shiftIsOpen();
            collection.setShift(shiftService.getOpenShift());
            cashRegisterRestService.sendIOCheck(collection);
            collectionService.save(collection);
            return ResponseEntity.status(HttpStatus.OK).body("Успешно");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/addUserToShift")
    public ResponseEntity<?> AddUserToShift(@RequestBody String login) {
        try {
            shiftService.shiftIsOpen();
            shiftService.addUser(shiftService.getOpenShift(), login);
            return ResponseEntity.status(HttpStatus.OK).body("Успешно");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/getXReport")
    public ResponseEntity<String> getXReport() {
        try {
            shiftService.shiftIsOpen();
            cashRegisterRestService.sendXReport();
            return ResponseEntity.ok("X-отчёт успешно отправлен");

        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/getListOfUsers")
    public ResponseEntity<String> getListOfUsers() {
        try {
            shiftService.shiftIsOpen();
            return ResponseEntity.ok(shiftService.getListOfUsers());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
