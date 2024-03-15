package com.yulkost.service.dto.mapper;

import com.yulkost.service.dto.ShiftReportDto;
import com.yulkost.service.model.Orders;
import com.yulkost.service.model.Shift;
import com.yulkost.service.model.User;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@Service
public class ShiftReportDtoMapper implements Function<Shift, ShiftReportDto> {

    @Override
    public ShiftReportDto apply(Shift shift) {
        String shiftState = shift.isStateOfShift() ? "Закрыта" : "Открыта";


        StringBuilder usersOnShift = new StringBuilder();
        for(User user: shift.getUsers()){
            usersOnShift
                    .append(user.getName())
                    .append(", ");
        }


        int sumEstablishmentPaid = 0;
        int sumCashPaid = 0;
        int sumCashLessPaid = 0;
        for (Orders order:shift.getOrders())
        {
            sumCashLessPaid += order.getCashLessPaid();
            sumCashPaid += order.getCashPaid();
            sumEstablishmentPaid += order.getEstablishmentPaid();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate = shift.getStartDate();
        LocalDateTime endDate = shift.getEndDate();

        String startDateS = null;
        String endDateS = null;
        if(startDate!=null){
            startDateS =  startDate.format(formatter);
        }
        if(endDate!=null){
            endDateS =  endDate.format(formatter);
        }


        return new ShiftReportDto(
                startDateS,
                endDateS,
                shiftState,
                usersOnShift.toString(),
                getIntToPage(sumCashPaid),
                        getIntToPage(sumCashLessPaid),
                                getIntToPage(sumEstablishmentPaid)
        );
    }
    public String getIntToPage(int sum){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("0.00",symbols).format(((float)sum)/100);
    }
}
