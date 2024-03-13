package com.yulkost.service.service;

import com.yulkost.service.model.OrderItems;
import com.yulkost.service.model.Orders;
import com.yulkost.service.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemsService {
    private final OrdersRepository ordersRepository;

    public OrderItemsService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public List<OrderItems> findAllSameByDate(LocalDateTime startDate, LocalDateTime endDate) {
        ordersRepository.findByDateBetween(startDate,endDate);
        return mergeSameOrderItems(ordersRepository.findByDateBetween(startDate,endDate));
    }

    private List<OrderItems> mergeSameOrderItems(List<Orders> orders) {
        List<OrderItems> orderItems = new ArrayList<>();
        boolean flag;
        for (Orders order :
                orders) {
            for (OrderItems orderItemInOrder :
                    order.getOrderItems()) {
                    flag = false;
                    for (OrderItems orderItemsArray :
                            orderItems) {
                        if (orderItemInOrder.getDateOfItemChange()==orderItemsArray.getDateOfItemChange() && orderItemInOrder.getItem()==orderItemsArray.getItem()) {
                            orderItemsArray.setQuantity(orderItemInOrder.getQuantity() + orderItemsArray.getQuantity());
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        orderItems.add(orderItemInOrder);
                    }
            }
        }
        return orderItems;
    }
}
