package com.srt.FreelanceMarketplace.domain.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtRequest {
    @NotBlank
    private String refreshToken;
}
