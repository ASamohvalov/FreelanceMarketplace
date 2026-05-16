package com.srt.FreelanceMarketplace.domain.dto.response.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BalanceRequest {
    private long balance;
    private long numberOfPoints;
}
