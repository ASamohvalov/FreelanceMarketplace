package com.srt.FreelanceMarketplace.domain.dto.response.feedback;

import com.srt.FreelanceMarketplace.domain.dto.response.user.UserNameResponse;
import com.srt.FreelanceMarketplace.domain.dto.typeEnum.FeedbackTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GetFeedbackResponse {
    private UUID id;
    private String text;
    private String title;
    private FeedbackTypeEnum type;
    private boolean accepted;
    private UserNameResponse sender;
    private Instant createdAt;
}
