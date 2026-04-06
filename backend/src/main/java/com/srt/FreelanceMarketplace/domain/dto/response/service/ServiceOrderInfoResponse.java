package com.srt.FreelanceMarketplace.domain.dto.response.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ServiceOrderInfoResponse {
    private UUID id;
    private String title;
    private int price;
    private String imageURL;
}
