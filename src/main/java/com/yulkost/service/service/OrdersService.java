package com.yulkost.service.service;

import com.yulkost.service.model.Orders;
import com.yulkost.service.repository.OrdersRepository;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {
    public OrdersRepository ordersRepository;

    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public void save(Orders order){
        ordersRepository.save(order);
    }
}
