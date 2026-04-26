package com.srt.FreelanceMarketplace.domain.dto.request.freelancer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class EditFreelancerProfileRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private UUID jobTitleId;
    @NotBlank
    private String aboutYourself;
}
