package com.srt.FreelanceMarketplace.domain.dto.response.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderReportResponse {
    private UUID id;
    private String report;
    private Instant createdAt;
}
