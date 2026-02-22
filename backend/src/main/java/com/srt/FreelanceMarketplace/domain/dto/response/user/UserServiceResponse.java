package com.srt.FreelanceMarketplace.domain.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserServiceResponse {
    private UUID id;
    private String title;
    private String description;
    private int price;
}
