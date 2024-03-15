package com.yulkost.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderItemsReportDto(
        @JsonProperty("Категория")
        String category,
        @JsonProperty("Наименование")
        String nameOfItems,
        @JsonProperty("Ед.из.")
        String unit,
        @JsonProperty("Кол-во")
        String quantity,
        @JsonProperty("Цена")
        String price,
        @JsonProperty("Сумма")
        String sumPrice
){
}
