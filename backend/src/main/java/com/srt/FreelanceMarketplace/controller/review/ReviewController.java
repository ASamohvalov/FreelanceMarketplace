package com.srt.FreelanceMarketplace.controller.review;

import com.srt.FreelanceMarketplace.domain.dto.request.review.SendReviewRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.review.ReviewResponse;
import com.srt.FreelanceMarketplace.service.application.review.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/send")
    public void sendReview(@RequestBody @Valid SendReviewRequest request) {
        reviewService.sendReview(request);
    }

    @PutMapping("/edit")
    public void editReview(@RequestBody @Valid SendReviewRequest request) {
        reviewService.sendReview(request);
    }

    @GetMapping("/get/by_service/{serviceId}")
    public List<ReviewResponse> getReviewByService(@PathVariable UUID serviceId) {
        return reviewService.getReviewByService(serviceId);
    }
}
