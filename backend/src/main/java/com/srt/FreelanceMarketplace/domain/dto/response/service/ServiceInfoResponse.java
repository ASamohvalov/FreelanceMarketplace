package com.srt.FreelanceMarketplace.domain.dto.response.service;

import com.srt.FreelanceMarketplace.domain.dto.response.FreelancerResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ServiceInfoResponse {
    private UUID id;
    private String title;
    private int price;
    private String description;
    private String category;
    private String subcategory;
    private FreelancerResponse freelancer;
    private boolean proposalBeenSent;
}
