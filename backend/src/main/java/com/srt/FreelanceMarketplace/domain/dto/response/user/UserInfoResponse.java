package com.srt.FreelanceMarketplace.domain.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    private String email;
    private String firstName;
    private String lastName;
    private List<UserServiceResponse> services;
}
