package com.srt.FreelanceMarketplace.domain.dto.response.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRejectResponse {
    private boolean rejected = false;
}
