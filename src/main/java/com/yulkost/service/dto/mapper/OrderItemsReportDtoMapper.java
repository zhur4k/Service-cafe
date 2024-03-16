package com.yulkost.service.dto.mapper;

import com.yulkost.service.dto.OrderItemsReportDto;
import com.yulkost.service.model.OrderItems;
import com.yulkost.service.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OrderItemsReportDtoMapper implements Function<OrderItems, OrderItemsReportDto> {
    private final OrdersRepository ordersRepository;

    public OrderItemsReportDtoMapper(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    public OrderItemsReportDto apply(OrderItems orderItems) {
        String establish = "Нет";
        if(ordersRepository.findByOrderItems(orderItems).getEstablishmentPaid()>0){
            establish = "Да";

        }
        return new OrderItemsReportDto(
                orderItems.getCategory(),
                orderItems.getNameOfItems(),
                orderItems.getUnit(),
                String.valueOf(orderItems.getQuantity()),
                orderItems.getPriceToPage(),
                orderItems.getSumToPage(),
                establish
        );
    }
}
