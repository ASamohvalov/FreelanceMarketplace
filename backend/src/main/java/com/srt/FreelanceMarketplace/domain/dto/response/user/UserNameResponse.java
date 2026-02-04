package com.srt.FreelanceMarketplace.domain.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserNameResponse {
    private UUID id;
    private String firstName;
    private String lastName;
}
