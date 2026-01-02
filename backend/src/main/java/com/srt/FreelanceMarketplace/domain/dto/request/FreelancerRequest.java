package com.srt.FreelanceMarketplace.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FreelancerRequest {
    @NotBlank
    private String jobTitle;
    @NotBlank
    private String phoneNumber;
}
