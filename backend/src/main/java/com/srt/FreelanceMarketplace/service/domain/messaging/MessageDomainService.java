package com.srt.FreelanceMarketplace.service.domain.messaging;

import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.MessageEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.repository.messaging.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

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
                .isRead(false)
                .build();
        repository.save(message);
    }
}
