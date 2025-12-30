package com.srt.FreelanceMarketplace.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceRequest {
    private String title;
    private String description;
    private String price;
}
