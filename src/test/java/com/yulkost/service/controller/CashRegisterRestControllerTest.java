package com.yulkost.service.controller;

import com.yulkost.service.service.CashRegisterRestService;
import com.yulkost.service.service.OrdersService;
import com.yulkost.service.service.YulkostTelegramBotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CashRegisterRestControllerTest {
    @Mock
    CashRegisterRestService cashRegisterService;
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