package com.srt.FreelanceMarketplace.domain.dto.response.messaging;

import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceTitleResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserNameResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ProposalResponse {
    private UUID id;
    private String description;
    private UserNameResponse author;
    private ServiceTitleResponse service;
}
