package com.srt.FreelanceMarketplace.domain.dto.response.review;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ReviewCheckResponse {
    private boolean canReview;
    private ReviewCheckActionEnum action;
    private UUID orderId;
}
