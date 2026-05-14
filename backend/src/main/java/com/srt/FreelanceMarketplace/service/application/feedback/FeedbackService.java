package com.srt.FreelanceMarketplace.service.application.feedback;

import com.srt.FreelanceMarketplace.domain.dto.request.feedback.SendFeedbackRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.feedback.GetFeedbackResponse;
import com.srt.FreelanceMarketplace.domain.entities.feedback.FeedbackEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.mapper.FeedbackMapper;
import com.srt.FreelanceMarketplace.repository.FeedbackRepository;
import com.srt.FreelanceMarketplace.service.domain.feedback.FeedbackDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository repository;
    private final FeedbackMapper mapper;
    private final FeedbackDomainService domainService;

    private final AuthHelperService authHelperService;

    public void sendFeedback(SendFeedbackRequest request) {
        UserEntity user = authHelperService.getUser();
        FeedbackEntity feedback = FeedbackEntity.builder()
                .type(request.getType())
                .text(request.getText())
                .title(request.getTitle())
                .sender(user)
                .build();
        repository.save(feedback);
    }

    public List<GetFeedbackResponse> getAll() {
        return repository.findAllByDeletedFalse().stream()
                .map(mapper::toGetResponse)
                .toList();
    }

    public GetFeedbackResponse getById(UUID id) {
        return mapper.toGetResponse(domainService.getById(id));
    }

    public void acceptFeedback(UUID id) {
        FeedbackEntity feedback = domainService.getById(id);
        feedback.setAccepted(true);
        repository.save(feedback);
    }

    public void deleteFeedback(UUID id) {
        FeedbackEntity feedback = domainService.getById(id);
        feedback.setDeleted(true);
        repository.save(feedback);
    }
}
