package com.srt.FreelanceMarketplace.domain.dto.request.order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class MakeOrderRequest {
    @NotNull
    private UUID serviceId;
    @NotNull
    private Instant deadlineDate;
}
