package com.srt.FreelanceMarketplace.domain.dto.response.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ServiceTitleResponse {
    private UUID id;
    private String title;
}
