package com.srt.FreelanceMarketplace.domain.dto.request.messaging;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ProposalRequest {
    @NotNull
    private UUID serviceId;
    @NotBlank
    private String description;
}
