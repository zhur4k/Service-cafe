package com.yulkost.service.dto;

import java.time.LocalDateTime;

public record DateFromPage(
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
