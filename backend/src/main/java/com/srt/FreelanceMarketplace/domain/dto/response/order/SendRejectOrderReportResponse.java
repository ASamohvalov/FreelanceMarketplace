package com.srt.FreelanceMarketplace.domain.dto.response.order;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendRejectOrderReportResponse {
    @NotBlank
    private String comment;
}
