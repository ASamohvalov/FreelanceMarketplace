package com.srt.FreelanceMarketplace.domain.dto.response.service;

import com.srt.FreelanceMarketplace.domain.dto.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderResponse {
    private UUID id;
    private OrderStatusEnum status;
    private ServiceResponse service;
}
