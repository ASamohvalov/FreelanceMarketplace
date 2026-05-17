package com.srt.FreelanceMarketplace.domain.dto.request.finance;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetPointRateRequest {
    private long pointRate;
}
