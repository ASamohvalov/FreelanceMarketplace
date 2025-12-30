package com.srt.FreelanceMarketplace.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FreelancerResponse {
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String phoneNumber;
}
