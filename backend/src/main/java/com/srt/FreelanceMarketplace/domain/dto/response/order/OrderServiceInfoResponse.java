package com.srt.FreelanceMarketplace.domain.dto.response.order;

import com.srt.FreelanceMarketplace.domain.dto.statusEnum.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderServiceInfoResponse {
    private boolean empty = true;
    private String serviceTitle;
    private int price;
    private Instant deadlineDate;
    private Instant orderDate;
    private OrderStatusEnum status;
}
