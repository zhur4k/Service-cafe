package com.yulkost.service.service;

import com.yulkost.service.model.OrderItems;
import com.yulkost.service.model.Orders;
import com.yulkost.service.repository.OrderItemsRepository;
import com.yulkost.service.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersService {
    public OrdersRepository ordersRepository;
    public ItemsService itemsService;
    public OrderItemsRepository orderItemsRepository;

    public OrdersService(OrdersRepository ordersRepository, ItemsService itemsService, OrderItemsRepository orderItemsRepository) {
        this.ordersRepository = ordersRepository;
        this.itemsService = itemsService;
        this.orderItemsRepository = orderItemsRepository;
    }

    public Orders OrderFromPageToOrders(Orders order){
        if(order.getOrderItems().isEmpty())
            return null;
        Orders newOrder = new Orders();
        newOrder.setDate(LocalDateTime.now());
        newOrder.setPaymentMethod(order.getPaymentMethod());
        newOrder.setCashier(order.getCashier());
        List<OrderItems> orderItems = new ArrayList<>();

        for (int i = 0; i < order.getOrderItems().size(); i++) {
            OrderItems orderItem = new OrderItems();
            orderItem.setItems(itemsService.findByNameOfItem(order.getOrderItems().get(i).getItems().getNameOfItems()));
            orderItem.setQuantity(order.getOrderItems().get(i).getQuantity());
            orderItems.add(orderItem);
        }

        newOrder.setOrderItems(orderItems);
        return newOrder;
    }
        public void save(Orders order){
        ordersRepository.save(order);
    }
}
