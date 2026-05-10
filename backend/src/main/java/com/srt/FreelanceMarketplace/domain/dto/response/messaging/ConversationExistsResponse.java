package com.srt.FreelanceMarketplace.domain.dto.response.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationExistsResponse {
    private UUID id;
    private boolean exists = false;
}
