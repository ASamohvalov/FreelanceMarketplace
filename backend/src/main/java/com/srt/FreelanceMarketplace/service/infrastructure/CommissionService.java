package com.srt.FreelanceMarketplace.service.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CommissionService {
    @Value("${payment.commission.percent}")
    private int commissionPercent;

    public long getFinalPrice(long price) {
        return price + (price * commissionPercent / 100);
    }

    public long getPriceWithoutCommission(long price) {
        return (price * 100) / (100 + commissionPercent);
    }

    public long getCommission(long price) {
        return price / commissionPercent;
    }
}
