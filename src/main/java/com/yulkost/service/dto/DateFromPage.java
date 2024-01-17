package com.yulkost.service.dto;

import java.time.LocalDate;

public record DateFromPage(
                           LocalDate startDate,
                           LocalDate endDate
) {
}
