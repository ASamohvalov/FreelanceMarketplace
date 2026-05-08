package com.srt.FreelanceMarketplace.domain.dto.response.order;

import com.srt.FreelanceMarketplace.domain.dto.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderResponse {
    private UUID id;
    private Instant orderDate;
    private Instant deadlineDate;
    private Instant completionDate;
    private OrderStatusEnum status;
    private boolean rejectByCustomer;
    private boolean rejectByFreelancer;
}
