package com.srt.FreelanceMarketplace.domain.dto.response.user;

import com.srt.FreelanceMarketplace.domain.dto.response.freelancer.GetFreelancerProfileResponse;
import com.srt.FreelanceMarketplace.domain.entities.user.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GetUserProfileResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private List<RoleEntity> roles;

    private GetFreelancerProfileResponse freelancer;
}
