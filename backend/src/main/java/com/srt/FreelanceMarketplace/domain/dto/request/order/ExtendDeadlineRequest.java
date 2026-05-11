package com.srt.FreelanceMarketplace.domain.dto.request.order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ExtendDeadlineRequest {
    @NotNull
    private UUID orderId;
    @NotNull
    private int daysAdded;
}
