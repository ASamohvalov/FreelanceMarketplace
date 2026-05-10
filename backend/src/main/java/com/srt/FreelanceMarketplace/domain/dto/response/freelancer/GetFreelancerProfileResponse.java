package com.srt.FreelanceMarketplace.domain.dto.response.freelancer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFreelancerProfileResponse {
    private UUID freelancerId;
    private String aboutYourself;
    private String jobTitle;
    private UUID jobTitleId;

    private FreelancerProfileStatisticResponse statistic;
}
