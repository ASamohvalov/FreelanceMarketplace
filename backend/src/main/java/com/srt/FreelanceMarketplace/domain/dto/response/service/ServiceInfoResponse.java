package com.srt.FreelanceMarketplace.domain.dto.response.service;

import com.srt.FreelanceMarketplace.domain.dto.response.FreelancerResponse;
import com.srt.FreelanceMarketplace.domain.dto.typeEnum.ServiceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
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
    private UUID categoryId;
    private UUID subcategoryId;
    private FreelancerResponse freelancer;
    private boolean proposalBeenSent;
    private List<UUID> imageIds;
    private ServiceTypeEnum type;
}
