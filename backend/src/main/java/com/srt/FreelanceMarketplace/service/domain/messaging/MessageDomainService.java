package com.srt.FreelanceMarketplace.service.domain.messaging;

import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.MessageEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.messaging.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageDomainService {
    private final MessageRepository repository;

    public void sendMessageByAuthor(
            ConversationEntity conversation,
            String messageText,
            UserEntity author
    ) {
        MessageEntity message = MessageEntity.builder()
                .conversation(conversation)
                .author(author)
                .message(messageText)
                .sendAt(Instant.now())
                .read(false)
                .build();
        repository.save(message);
    }

    public MessageEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new GlobalBadRequestException("this message not found"));
    }

    public MessageEntity getReferenceIfExistsById(UUID id) {
        if (!repository.existsById(id)) {
            throw  new GlobalBadRequestException("this message not found");
        }
        return repository.getReferenceById(id);
    }
}
