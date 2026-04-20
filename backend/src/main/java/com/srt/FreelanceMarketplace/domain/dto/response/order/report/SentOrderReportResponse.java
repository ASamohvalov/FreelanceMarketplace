package com.srt.FreelanceMarketplace.domain.dto.response.order.report;

import com.srt.FreelanceMarketplace.domain.dto.OrderReportStatusEnum;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserNameResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SentOrderReportResponse {
    private UUID id;
    private String title;
    private String report;
    private Instant createdAt;
    private OrderReportStatusEnum status;
    private UserNameResponse user;
}
