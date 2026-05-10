package com.srt.FreelanceMarketplace.domain.dto.response.freelancer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class FreelancerProfileStatisticResponse {
    private int orderAmount;
    private double rating;
    private Instant accountCreatedAt;
}
