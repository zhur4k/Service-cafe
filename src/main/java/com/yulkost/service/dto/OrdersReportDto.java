package com.yulkost.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrdersReportDto(
        @JsonProperty("Дата")
        String date,
        @JsonProperty("Наличные")
        String sumCashPaid,
        @JsonProperty("Безнал")
        String sumCashLessPaid,
        @JsonProperty("За счёт заведения")
        String sumEstablishmentPaid,
        @JsonProperty("Наличные")
        String sumOfChange
){
}
