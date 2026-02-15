package com.srt.FreelanceMarketplace.domain.dto.response.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class MessageResponse {
    private UUID id;
    private String text;
    private Instant sendAt;
    private UUID authorId;
}
