package com.yulkost.service.controller;

import com.yulkost.service.model.Orders;
import com.yulkost.service.model.User;
import com.yulkost.service.service.CashRegisterService;
import com.yulkost.service.service.OrdersService;
import com.yulkost.service.service.YulkostTelegramBotService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CashRegisterRestController {

    public CashRegisterService cashRegisterService;
    public OrdersService ordersService;
    public YulkostTelegramBotService yulkostTelegramBotService;

    public CashRegisterRestController(CashRegisterService cashRegisterService, OrdersService ordersService, YulkostTelegramBotService yulkostTelegramBotService) {
        this.cashRegisterService = cashRegisterService;
        this.ordersService = ordersService;
        this.yulkostTelegramBotService = yulkostTelegramBotService;
    }

    @PostMapping("/submitOrder")
    public String SubmitOrder(@RequestBody Orders order,@AuthenticationPrincipal User user) {
        order.setCashier(user.getName());
        Orders orderToSave= ordersService.OrderFromPageToOrders(order);

        if(cashRegisterService.SendFCheck(orderToSave)){
            yulkostTelegramBotService.SendOrderToUser(user,ordersService.save(orderToSave));
            return "true";
        }
        return "false";
    }
}
