package com.srt.FreelanceMarketplace.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FreelancerResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String jobTitle;
}
