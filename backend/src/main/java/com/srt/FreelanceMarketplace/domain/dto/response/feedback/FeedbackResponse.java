package com.srt.FreelanceMarketplace.domain.dto.response.feedback;

import com.srt.FreelanceMarketplace.domain.dto.typeEnum.FeedbackTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class FeedbackResponse {
    private UUID id;
    private FeedbackTypeEnum type;
    private String title;
    private String text;
    private boolean accepted;
    private Instant createdAt;
}
