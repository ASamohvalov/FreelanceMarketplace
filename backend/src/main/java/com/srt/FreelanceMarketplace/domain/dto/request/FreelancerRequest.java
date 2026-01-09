package com.srt.FreelanceMarketplace.domain.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FreelancerRequest {
    @NotNull
    private UUID jobTitleId;
    @NotBlank
    @Max(11)
    private String phoneNumber;
}
