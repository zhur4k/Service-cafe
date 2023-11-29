package com.yulkost.service.controller;

import com.yulkost.service.service.CashRegisterService;
import com.yulkost.service.service.OrdersService;
import com.yulkost.service.service.YulkostTelegramBotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CashRegisterRestControllerTest {
    @Mock
    CashRegisterService cashRegisterService;
    @Mock
    OrdersService ordersService;
    @Mock
    YulkostTelegramBotService yulkostTelegramBotService;
    @InjectMocks
    CashRegisterRestController controller;
    @Test
    void submitOrder() {
    }
}