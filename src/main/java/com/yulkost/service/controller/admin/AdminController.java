package com.yulkost.service.controller.admin;

import com.yulkost.service.dto.DateFromPage;
import com.yulkost.service.service.OrderItemsService;
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

    public AdminController(OrderItemsService orderItemsService) {
        this.orderItemsService = orderItemsService;
    }

    @GetMapping
    public String Main() {
        return "admin"; }

    @PostMapping("/getAllOrderItems")
    public ResponseEntity<?> getAllOrderItemsByDate(@RequestBody DateFromPage dateFromPage) {
        try {
            return ResponseEntity.ok(orderItemsService.findAllSameByDate(dateFromPage.startDate(),dateFromPage.endDate()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // Ошибка, отправьте соответствующий HTTP-статус
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
