package com.yulkost.service.controller.admin;

import com.yulkost.service.dto.DateFromPage;
import com.yulkost.service.service.OrderItemsService;
import com.yulkost.service.service.OrdersService;
import com.yulkost.service.service.ShiftService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final OrderItemsService orderItemsService;
    private final ShiftService shiftService;
    private final OrdersService ordersService;

    public AdminController(OrderItemsService orderItemsService, ShiftService shiftService, OrdersService ordersService) {
        this.orderItemsService = orderItemsService;
        this.shiftService = shiftService;
        this.ordersService = ordersService;
    }

    @GetMapping
    public String Main() {
        return "admin"; }

    @PostMapping("/getAllOrderItems")
    public ResponseEntity<?> getAllOrderItemsByDate(@RequestBody DateFromPage dateFromPage) {
        try {
            return ResponseEntity.ok(orderItemsService.findAllSameByDate(dateFromPage.startDate(),dateFromPage.endDate()));
        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/getAllShifts")
    public ResponseEntity<?> getAllShiftsByDate(@RequestBody DateFromPage dateFromPage) {
        try {
            return ResponseEntity.ok(shiftService.findAllSameByDate(dateFromPage.startDate(),dateFromPage.endDate()));
        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/getAllOrders")
    public ResponseEntity<?> getAllOrdersByDate(@RequestBody DateFromPage dateFromPage) {
        try {
            return ResponseEntity.ok(ordersService.findAllSameByDate(dateFromPage.startDate(),dateFromPage.endDate()));
        } catch (Exception e) {
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
