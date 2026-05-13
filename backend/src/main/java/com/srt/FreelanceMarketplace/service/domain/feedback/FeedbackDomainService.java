package com.srt.FreelanceMarketplace.service.domain.feedback;

import com.srt.FreelanceMarketplace.domain.entities.feedback.FeedbackEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackDomainService {
    private final FeedbackRepository repository;

    public FeedbackEntity getById(UUID id) {
        return repository.findById(id)
                        .orElseThrow(() -> new GlobalBadRequestException("feedback not found"));
    }

    public FeedbackEntity getReferenceIfExistsById(UUID id) {
        if (!repository.existsById(id)) {
            throw new GlobalBadRequestException("feedback not found");
        }
        return repository.getReferenceById(id);
    }
}
