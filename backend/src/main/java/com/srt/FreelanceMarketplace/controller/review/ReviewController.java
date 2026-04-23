package com.srt.FreelanceMarketplace.controller.review;

import com.srt.FreelanceMarketplace.domain.dto.request.review.EditReviewRequest;
import com.srt.FreelanceMarketplace.domain.dto.request.review.SendReviewRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.review.ReviewResponse;
import com.srt.FreelanceMarketplace.service.application.review.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/send")
    @PreAuthorize("isAuthenticated()")
    public void sendReview(@RequestBody @Valid SendReviewRequest request) {
        reviewService.sendReview(request);
    }

    @PutMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public void editReview(@RequestBody @Valid EditReviewRequest request) {
        reviewService.editReview(request);
    }

    @GetMapping("/get/by_order/{orderId}")
    public ReviewResponse getReviewByOrder(@PathVariable UUID orderId) {
        return reviewService.getReviewByOrder(orderId);
    }

    @GetMapping("/get/personal/by_service/{serviceId}")
    public ReviewResponse getPersonalReviewByService(@PathVariable UUID serviceId) {
        return reviewService.getPersonalReviewByService(serviceId);
    }

    @GetMapping("/get/by_service/{serviceId}")
    public List<ReviewResponse> getReviewsByService(@PathVariable UUID serviceId) {
        return reviewService.getReviewsByService(serviceId);
    }
}
