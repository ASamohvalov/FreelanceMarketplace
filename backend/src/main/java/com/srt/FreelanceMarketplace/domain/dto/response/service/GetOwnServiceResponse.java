package com.srt.FreelanceMarketplace.domain.dto.response.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOwnServiceResponse {
    private UUID id;
    private String title;
    private int price;
    private String imageURL;
    private boolean isHide;
}
