package com.srt.FreelanceMarketplace.domain.dto.response.order;

import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCustomerResponse {
    private OrderResponse order;
    private ServiceResponse service;
}
