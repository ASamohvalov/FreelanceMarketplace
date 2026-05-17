package com.srt.FreelanceMarketplace.domain.dto.response.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BalanceResponse {
    private long balance;
    private long numberOfPoints;
}
