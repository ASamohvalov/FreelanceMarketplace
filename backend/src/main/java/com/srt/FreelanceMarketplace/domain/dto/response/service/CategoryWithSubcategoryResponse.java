package com.srt.FreelanceMarketplace.domain.dto.response.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CategoryWithSubcategoryResponse {
    private UUID id;
    private String name;
    private List<CategoryResponse> subcategories;
}
