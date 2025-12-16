package com.srt.FreelanceExchange.domain.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
