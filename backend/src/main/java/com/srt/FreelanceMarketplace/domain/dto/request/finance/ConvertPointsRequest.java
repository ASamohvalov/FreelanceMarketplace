package com.srt.FreelanceMarketplace.domain.dto.request.finance;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConvertPointsRequest {
    @NotNull
    @Min(1)
    private long numberOfPoints;
}
