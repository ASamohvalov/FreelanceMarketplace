package com.srt.FreelanceMarketplace.domain.dto.request.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SubcategoryRequest {
    @NotBlank
    private String name;
    @NotNull
    private UUID categoryId;
}
