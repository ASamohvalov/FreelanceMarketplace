package com.srt.FreelanceMarketplace.controller.feedback;

import com.srt.FreelanceMarketplace.domain.dto.request.feedback.SendFeedbackRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.feedback.GetFeedbackResponse;
import com.srt.FreelanceMarketplace.service.application.feedback.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping("/send")
    public void sendFeedback(@RequestBody @Valid SendFeedbackRequest request) {
        feedbackService.sendFeedback(request);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @GetMapping("/get")
    public List<GetFeedbackResponse> getAll() {
        return feedbackService.getAll();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @GetMapping("/get/{id}")
    public GetFeedbackResponse getById(@PathVariable UUID id) {
        return feedbackService.getById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @PatchMapping("/accept/{id}")
    public void acceptFeedback(@PathVariable UUID id) {
        feedbackService.acceptFeedback(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @DeleteMapping("/delete/{id}")
    public void deleteFeedback(@PathVariable UUID id) {
        feedbackService.deleteFeedback(id);
    }
}
