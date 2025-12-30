package com.srt.FreelanceMarketplace.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceResponse {
    private FreelancerResponse freelancer;
    private String title;
    private String description;
    private int price;
}
