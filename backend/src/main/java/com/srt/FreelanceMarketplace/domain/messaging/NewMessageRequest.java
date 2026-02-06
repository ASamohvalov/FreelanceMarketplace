package com.srt.FreelanceMarketplace.domain.messaging;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class NewMessageRequest {
    @NotNull
    private UUID conversationId;
    @NotBlank
    private String message;
}
