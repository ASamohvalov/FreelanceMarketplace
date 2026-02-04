package com.srt.FreelanceMarketplace.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class JobTitleResponse {
    private UUID id;
    private String name;
}
