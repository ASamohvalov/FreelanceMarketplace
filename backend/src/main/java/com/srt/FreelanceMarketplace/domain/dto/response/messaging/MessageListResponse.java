package com.srt.FreelanceMarketplace.domain.dto.response.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MessageListResponse {
    private List<MessageResponse> messages;
    private long lastEventId;
}
