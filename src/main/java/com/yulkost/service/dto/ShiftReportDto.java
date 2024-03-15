package com.yulkost.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ShiftReportDto(
        @JsonProperty("Открытие")
        String startDate,
        @JsonProperty("Закрытие")
        String endDate,
        @JsonProperty("Статус")
        String stateOfShift,
        @JsonProperty("Работники")
        String users,
        @JsonProperty("Наличные")
        String sumCashPaid,
        @JsonProperty("Безнал")
        String sumCashLessPaid,
        @JsonProperty("За счёт заведения")
        String sumEstablishmentPaid
){
}
