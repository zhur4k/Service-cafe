package com.yulkost.service.dto.mapper;

import com.yulkost.service.dto.OrderItemsReportDto;
import com.yulkost.service.model.OrderItems;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OrderItemsReportDtoMapper implements Function<OrderItems, OrderItemsReportDto> {

    @Override
    public OrderItemsReportDto apply(OrderItems orderItems) {
        return new OrderItemsReportDto(
                orderItems.getCategory(),
                orderItems.getNameOfItems(),
                orderItems.getUnit(),
                String.valueOf(orderItems.getQuantity()),
                orderItems.getPriceToPage(),
                orderItems.getSumToPage()
        );
    }
}
