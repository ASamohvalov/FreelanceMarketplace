package com.srt.FreelanceMarketplace.domain.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditUserProfileRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
