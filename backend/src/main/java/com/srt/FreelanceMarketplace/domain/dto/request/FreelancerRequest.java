package com.srt.FreelanceMarketplace.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FreelancerRequest {
    @NotNull
    private UUID jobTitleId;
    @NotBlank
    @Size(max = 11)
    private String phoneNumber;
}
