package com.srt.FreelanceMarketplace.domain.dto.response.review;

import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceTitleResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserNameResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private UUID id;
    private int rating;
    private String review;
    private Instant sendAt;
    private UserNameResponse author;

    private ServiceTitleResponse service; // can be null
}
