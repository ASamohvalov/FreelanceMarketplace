package com.srt.FreelanceExchange.domain.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
