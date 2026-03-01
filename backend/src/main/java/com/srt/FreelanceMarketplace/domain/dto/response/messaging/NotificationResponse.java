package com.srt.FreelanceMarketplace.domain.dto.response.messaging;

import com.srt.FreelanceMarketplace.domain.dto.NotificationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class NotificationResponse {
    private UUID id;
    private String title;
    private String message;
    private Instant sendAt;
    private NotificationTypeEnum type;
    private UUID entityId;
}
