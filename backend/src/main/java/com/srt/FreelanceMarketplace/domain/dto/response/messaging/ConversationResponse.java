package com.srt.FreelanceMarketplace.domain.dto.response.messaging;

import com.srt.FreelanceMarketplace.domain.dto.response.user.UserNameResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ConversationResponse {
    private UUID id;
    private UserNameResponse member;
}
