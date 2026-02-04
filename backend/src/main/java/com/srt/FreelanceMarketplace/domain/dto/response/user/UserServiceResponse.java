package com.srt.FreelanceMarketplace.domain.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserServiceResponse {
    private String title;
    private String description;
    private int price;
}
