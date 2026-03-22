package com.srt.FreelanceMarketplace.domain.dto.response.messaging;

import com.srt.FreelanceMarketplace.domain.dto.ConversationTypeEnum;
import com.srt.FreelanceMarketplace.domain.dto.response.order.OrderResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConversationContextResponse {
    private OrderResponse order;
    private ServiceResponse service;
    private ConversationTypeEnum type;
}
