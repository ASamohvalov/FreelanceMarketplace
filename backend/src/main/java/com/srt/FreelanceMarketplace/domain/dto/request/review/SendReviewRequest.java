package com.srt.FreelanceMarketplace.domain.dto.request.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SendReviewRequest {
    @NotNull
    private UUID orderId;

    @Max(5) @Min(1)
    private int rating;

    @NotBlank
    private String review;
}
