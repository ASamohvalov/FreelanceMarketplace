package com.srt.FreelanceMarketplace.service.domain.messaging;

import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.MessageEventEntity;
import com.srt.FreelanceMarketplace.repository.messaging.MessageEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageEventDomainService {
    private final MessageEventRepository repository;

    public long findLastId(ConversationEntity conversation) {
        Optional<MessageEventEntity> event = repository.findFirstByMessage_conversationOrderByIdDesc(conversation);
        return event.isPresent() ? event.get().getId() : 0;
    }

    public void saveAll(Iterable<MessageEventEntity> entities) {
        repository.saveAll(entities);
    }
}
