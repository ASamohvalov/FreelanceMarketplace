package com.srt.FreelanceMarketplace.domain.dto.response.order;

import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceOrderInfoResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserNameResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderFreelancerResponse {
    private OrderResponse order;
    private ServiceOrderInfoResponse service;
    private UserNameResponse customer;
}
