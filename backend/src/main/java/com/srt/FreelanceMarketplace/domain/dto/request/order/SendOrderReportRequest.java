package com.srt.FreelanceMarketplace.domain.dto.request.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SendOrderReportRequest {
    @NotNull
    private UUID orderId;
    @NotBlank
    private String report;
    @NotBlank
    private String title;
}
