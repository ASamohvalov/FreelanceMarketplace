package com.srt.FreelanceMarketplace.domain.dto.response.service;

import com.srt.FreelanceMarketplace.domain.dto.response.FreelancerResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse {
    private UUID id;
    private String title;
    private String description;
    private int price;
    private FreelancerResponse freelancer;
}
