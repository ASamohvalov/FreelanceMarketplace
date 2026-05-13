package com.srt.FreelanceMarketplace.domain.dto.response.user;

import com.srt.FreelanceMarketplace.domain.dto.typeEnum.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GetUserResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private List<RoleEnum> roles;
}
