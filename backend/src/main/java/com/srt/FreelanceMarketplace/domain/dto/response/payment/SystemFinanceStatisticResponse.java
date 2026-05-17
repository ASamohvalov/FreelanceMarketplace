package com.srt.FreelanceMarketplace.domain.dto.response.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SystemFinanceStatisticResponse {
    private Long currencyRate;
    private Long totalServiceEarnings;
    private Long totalPoints;
}
