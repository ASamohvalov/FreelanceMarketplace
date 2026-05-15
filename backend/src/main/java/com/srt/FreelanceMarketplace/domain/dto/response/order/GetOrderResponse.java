package com.srt.FreelanceMarketplace.domain.dto.response.order;

import com.srt.FreelanceMarketplace.domain.dto.response.user.UserNameResponse;
import com.srt.FreelanceMarketplace.domain.dto.statusEnum.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GetOrderResponse {
    private UUID id;
    private UserNameResponse customer;
    private UserNameResponse freelancer;
    private OrderStatusEnum status;
    private UUID serviceId;
    private String serviceTitle;
    private Instant orderDate;
}
