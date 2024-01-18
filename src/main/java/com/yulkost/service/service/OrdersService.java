package com.yulkost.service.service;

import com.yulkost.service.model.CashRegister;
import com.yulkost.service.model.OrderItems;
import com.yulkost.service.model.Orders;
import com.yulkost.service.repository.CashRegisterRepository;
import com.yulkost.service.repository.OrderItemsRepository;
import com.yulkost.service.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersService {
    public OrdersRepository ordersRepository;
    public ItemsService itemsService;
    public OrderItemsRepository orderItemsRepository;
    private CashRegisterRepository cashRegisterRepository;
    private ProductStockService productStockService;

    public OrdersService(OrdersRepository ordersRepository, ItemsService itemsService, OrderItemsRepository orderItemsRepository, CashRegisterRepository cashRegisterRepository, ProductStockService productStockService) {
        this.ordersRepository = ordersRepository;
        this.itemsService = itemsService;
        this.orderItemsRepository = orderItemsRepository;
        this.cashRegisterRepository = cashRegisterRepository;
        this.productStockService = productStockService;
    }

    public Orders OrderFromPageToOrders(Orders order){
        if(order.getOrderItems().isEmpty())
            return null;
        Orders newOrder = new Orders();
        newOrder.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        newOrder.setShift(order.getShift());
        newOrder.setCashPaid(order.getCashPaid());
        newOrder.setCashLessPaid(order.getCashLessPaid());
        newOrder.setEstablishmentPaid(order.getEstablishmentPaid());
        List<OrderItems> orderItems = new ArrayList<>();

        for (int i = 0; i < order.getOrderItems().size(); i++) {
            OrderItems orderItem = new OrderItems();
            orderItem.setItems(itemsService.findById(order.getOrderItems().get(i).getItems().getId()));
            orderItem.setQuantity(order.getOrderItems().get(i).getQuantity());
            orderItems.add(orderItem);
        }
        newOrder.setOrderItems(orderItems);
        return newOrder;
    }
        public void save(Orders order){
            CashRegister cashRegister = new CashRegister();
            CashRegister cashRegister1 = cashRegisterRepository.findCashRegisterWithMaxId();
            if(cashRegister1==null){
                cashRegister.setCashAmount(order.getCashPaid());
            }else{
                cashRegister.setCashAmount(cashRegister1.getCashAmount()+order.getCashPaid());
            }

            orderItemsRepository.saveAll(order.getOrderItems());
            Orders orders = ordersRepository.save(order);
            cashRegister.setOrder(orders);
            cashRegisterRepository.save(cashRegister);
            productStockService.writeOffProductFromStockAndSaveToStockMovement(order);
    }
}
