package com.srt.FreelanceMarketplace.domain.dto.request.feedback;

import com.srt.FreelanceMarketplace.domain.dto.typeEnum.FeedbackTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendFeedbackRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @NotNull
    private FeedbackTypeEnum type;
}
