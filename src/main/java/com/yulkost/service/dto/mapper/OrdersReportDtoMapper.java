package com.yulkost.service.dto.mapper;

import com.yulkost.service.dto.OrdersReportDto;
import com.yulkost.service.model.Orders;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@Service
public class OrdersReportDtoMapper implements Function<Orders, OrdersReportDto> {

    @Override
    public OrdersReportDto apply(Orders orders) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = orders.getDate();

        String dateS = null;
        if(date!=null){
            dateS =  date.format(formatter);
        }
        return new OrdersReportDto(
                dateS,
                getIntToPage(orders.getCashPaid()),
                getIntToPage(orders.getCashLessPaid()),
                getIntToPage(orders.getEstablishmentPaid()),
                getIntToPage(orders.getSumOfChange())
        );
    }
    public String getIntToPage(int sum){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.00",symbols).format(((float)sum)/100);
    }
}
