package com.srt.FreelanceMarketplace.domain.dto.response.order;

import com.srt.FreelanceMarketplace.domain.dto.OrderReportStatusEnum;
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
    private OrderReportStatusEnum status;
}
