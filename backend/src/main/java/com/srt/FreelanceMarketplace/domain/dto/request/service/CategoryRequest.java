package com.srt.FreelanceMarketplace.domain.dto.request.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryRequest {
    @NotBlank
    @Size(max = 50)
    private String name;
}
