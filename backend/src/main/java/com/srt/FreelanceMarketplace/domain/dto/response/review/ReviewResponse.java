package com.srt.FreelanceMarketplace.domain.dto.response.review;

import com.srt.FreelanceMarketplace.domain.dto.response.user.UserNameResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ReviewResponse {
    private UUID id;
    private int rating;
    private String review;
    private Instant createdAt;
    private UserNameResponse author;
}
