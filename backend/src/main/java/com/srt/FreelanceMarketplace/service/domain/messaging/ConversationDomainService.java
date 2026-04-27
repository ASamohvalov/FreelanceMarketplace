package com.srt.FreelanceMarketplace.service.domain.messaging;

import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.messaging.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationDomainService {
    private final ConversationRepository repository;

    public void save(ConversationEntity conversation) {
        repository.save(conversation);
    }

    public void throwIfNotExistsById(UUID id) {
        if (!repository.existsById(id)) {
            throw new GlobalBadRequestException("such conversation not found");
        }
    }

    public ConversationEntity getReferenceById(UUID id) {
        return repository.getReferenceById(id);
    }

    public ConversationEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new GlobalBadRequestException("such conversation not found"));
    }

    public ConversationEntity getByIdWithOrderAndServiceAndFreelancer(UUID id) {
        return repository.findByIdWithOrderAndServiceAndFreelancer(id)
                .orElseThrow(() -> new GlobalBadRequestException("such conversation not found"));
    }

    /**
     * @throws IllegalStateException the member ids should be correct
     */
    public ConversationEntity getByMemberIds(UUID member1Id, UUID member2Id) {
        return repository.findByMemberIds(member1Id, member2Id)
                .orElseThrow(() -> new IllegalStateException("conversation by this ids not found"));
    }
}
