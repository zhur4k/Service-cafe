package com.yulkost.service.controller;
import com.yulkost.service.dto.CategoriesDtoToPage;
import com.yulkost.service.model.*;
import com.yulkost.service.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CashRegisterRestControllerTest {

    @Mock
    private CashRegisterRestService cashRegisterRestService;

    @Mock
    private OrdersService ordersService;

    @Mock
    private YulkostTelegramBotService yulkostTelegramBotService;

    @Mock
    private CategoriesService categoriesService;

    @Mock
    private ItemsService itemsService;

    @Mock
    private CollectionService collectionService;

    @Mock
    private ShiftService shiftService;

    @Mock
    private CashRegisterService cashRegisterService;

    @InjectMocks
    private CashRegisterRestController cashRegisterRestController;

    @Test
    public void testGetCategory() {
        // Create an instance of Categories using setter methods
        CategoriesDtoToPage testCategory = new CategoriesDtoToPage(
                1L,
                "Test Category",
                null,
                1L,
                null
        );

        // Mock data
        List<CategoriesDtoToPage> categories = Collections.singletonList(testCategory);

        // Mock service behavior
        when(categoriesService.findAll()).thenReturn(categories);

        // Perform the test
        ResponseEntity<?> response = cashRegisterRestController.getCategory();

        // Assertions
        verify(categoriesService, times(1)).findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());
    }

    @Test
    public void testGetItemsToPage() {
        // Mock data
        // Create an instance of Items using setter methods
        Items testItem = new Items();
        testItem.setNameOfItems("Test Item");
        testItem.setPrice(100);

        // Mock data
        List<Items> items = Collections.singletonList(testItem);

        // Mock service behavior
        when(itemsService.findAllList()).thenReturn(items);

        // Perform the test
        ResponseEntity<?> response = cashRegisterRestController.getItemsToPage();

        // Assertions
        verify(itemsService, times(1)).findAllList();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(items, response.getBody());
    }

    @Test
    public void testGetOpenShift() {
        // Mock data
        Shift openShift = new Shift();

        // Mock service behavior
        when(shiftService.getOpenShift()).thenReturn(openShift);

        // Perform the test
        ResponseEntity<?> response = cashRegisterRestController.getOpenShift();

        // Assertions
        verify(shiftService, times(1)).getOpenShift();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(openShift, response.getBody());
    }

    @Test
    public void testOpenShift() {
        // Mock data
        User user = new User();
        user.setName("testUser");
        user.setPassword("password");

        // Mock service behavior
        Shift openShift = new Shift();
        when(shiftService.openShift(user)).thenReturn(openShift);

        // Perform the test
        ResponseEntity<Shift> response = cashRegisterRestController.openShift(user);

        // Assertions
        verify(shiftService, times(1)).openShift(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(openShift, response.getBody());
    }

    @Test
    public void testCloseShift() {
        // Mock data
        User user = new User();
        user.setName("testUser");
        user.setPassword("password");

        // Mock service behavior
        doNothing().when(cashRegisterRestService).sendZReport();
        doNothing().when(shiftService).closeShift();

        // Perform the test
        ResponseEntity<?> response = cashRegisterRestController.closeShift(user);

        // Assertions
        verify(cashRegisterRestService, times(1)).sendZReport();
        verify(shiftService, times(1)).closeShift();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success Shift was closed", response.getBody());
    }


    @Test
    public void testSumInCashRegister() {
        // Mock service behavior
        when(cashRegisterService.getSumInCashRegister()).thenReturn("100");

        // Perform the test
        ResponseEntity<String> response = cashRegisterRestController.sumInCashRegister();

        // Assertions
        verify(cashRegisterService, times(1)).getSumInCashRegister();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("100", response.getBody());
    }

    @Test
    public void testSubmitOrderWithEstablishmentPaid() {
        // Mock data
        Orders order = new Orders();
        order.setEstablishmentPaid(200);

        // Mock service behavior
        when(shiftService.getOpenShift()).thenReturn(new Shift());
        when(ordersService.OrderFromPageToOrders(order)).thenReturn(order);

        // Perform the test
        ResponseEntity<?> response = cashRegisterRestController.SubmitOrder(order);

        // Assertions
        verify(cashRegisterRestService, never()).sendFCheck(order);
        verify(ordersService, times(1)).save(order);
        verify(yulkostTelegramBotService, times(1)).SendOrderToUser(order);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Успешно", response.getBody());
    }

    @Test
    public void testSubmitOrderWithoutEstablishmentPaid() {
        // Mock data
        Orders order = new Orders();

        // Mock service behavior
        when(shiftService.getOpenShift()).thenReturn(new Shift());
        when(ordersService.OrderFromPageToOrders(order)).thenReturn(order);
        doNothing().when(cashRegisterRestService).sendFCheck(order);

        // Perform the test
        ResponseEntity<?> response = cashRegisterRestController.SubmitOrder(order);

        // Assertions
        verify(cashRegisterRestService, times(1)).sendFCheck(order);
        verify(ordersService, times(1)).save(order);
        verify(yulkostTelegramBotService, times(1)).SendOrderToUser(order);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Успешно", response.getBody());
    }

    @Test
    public void testCollectionMove() {
        // Mock data
        Collection collection = new Collection();

        // Mock service behavior
        when(shiftService.getOpenShift()).thenReturn(new Shift());
        doNothing().when(cashRegisterRestService).sendIOCheck(collection);
        doNothing().when(collectionService).save(collection);

        // Perform the test
        ResponseEntity<?> response = cashRegisterRestController.CollectionMove(collection);

        // Assertions
        verify(cashRegisterRestService, times(1)).sendIOCheck(collection);
        verify(collectionService, times(1)).save(collection);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Успешно", response.getBody());
    }

    @Test
    public void testAddUserToShift() {
        // Mock data
        String login = "testUser";

        // Mock service behavior
        when(shiftService.addUser(any(), eq(login)));

        // Perform the test
        when(shiftService.addUser(any(), eq(login))).thenReturn(new Shift());

        // Assertions
        verify(shiftService, times(1)).addUser(any(), eq(login));
    }

    @Test
    public void testGetXReport() {
        // Mock service behavior
        doNothing().when(cashRegisterRestService).sendXReport();

        // Perform the test
        ResponseEntity<String> response = cashRegisterRestController.getXReport();

        // Assertions
        verify(cashRegisterRestService, times(1)).sendXReport();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("X-отчёт успешно отправлен", response.getBody());
    }

    @Test
    public void testGetListOfUsers() {
        // Mock service behavior
        when(shiftService.getListOfUsers()).thenReturn("User1, User2, User3");

        // Perform the test
        ResponseEntity<String> response = cashRegisterRestController.getListOfUsers();

        // Assertions
        verify(shiftService, times(1)).getListOfUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User1, User2, User3", response.getBody());
    }
}
