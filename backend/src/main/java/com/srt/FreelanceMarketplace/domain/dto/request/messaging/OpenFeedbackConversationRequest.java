package com.srt.FreelanceMarketplace.domain.dto.request.messaging;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class OpenFeedbackConversationRequest {
    @NotNull
    private UUID userId;
    @NotNull
    private UUID feedbackId;
}
