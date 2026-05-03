package com.srt.FreelanceMarketplace.domain.dto;

import com.srt.FreelanceMarketplace.domain.dto.response.messaging.MessageResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class MessageEventDTO {
    private Long id;
    private MessageEventTypeEnum type;
    private MessageResponse message;
    private Instant createdAt;
}
