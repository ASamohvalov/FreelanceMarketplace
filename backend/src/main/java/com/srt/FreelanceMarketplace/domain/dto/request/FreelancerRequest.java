package com.srt.FreelanceMarketplace.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FreelancerRequest {
    private UUID jobTitleId;

    @NotBlank
    private String aboutYourself;
}
