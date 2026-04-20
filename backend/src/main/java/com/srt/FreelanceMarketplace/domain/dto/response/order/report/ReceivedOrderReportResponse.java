package com.srt.FreelanceMarketplace.domain.dto.response.order.report;

import com.srt.FreelanceMarketplace.domain.dto.OrderReportStatusEnum;
import com.srt.FreelanceMarketplace.domain.dto.response.FreelancerResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ReceivedOrderReportResponse {
    private UUID id;
    private String title;
    private String report;
    private Instant createdAt;
    private OrderReportStatusEnum status;
    private FreelancerResponse freelancer;
}
