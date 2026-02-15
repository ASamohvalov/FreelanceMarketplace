package com.srt.FreelanceMarketplace.service.entity.messaging;

import com.srt.FreelanceMarketplace.domain.dto.response.messaging.MessageResponse;
import com.srt.FreelanceMarketplace.domain.entities.messages.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.messages.MessageEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.domain.messaging.NewMessageRequest;
import com.srt.FreelanceMarketplace.mapper.MessageMapper;
import com.srt.FreelanceMarketplace.repository.messaging.MessageRepository;
import com.srt.FreelanceMarketplace.service.logic.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repository;
    private final MessageMapper mapper;

    private final ConversationService conversationService;
    private final AuthHelperService authHelperService;

    public void sendMessage(NewMessageRequest request) {
        MessageEntity message = MessageEntity.builder()
                .conversation(conversationService.getById(request.getConversationId()))
                .author(authHelperService.getUser())
                .message(request.getMessage())
                .sendAt(Instant.now())
                .build();
        repository.save(message);
    }

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
                .build();
        repository.save(message);
    }

    public List<MessageResponse> getMessages(UUID conversationId) {
        conversationService.throwIfNotExistsById(conversationId);

        ConversationEntity conversation = conversationService.getReferenceById(conversationId);

        return repository.findAllByConversation(conversation).stream()
                .map(mapper::fromEntity)
                .toList();
    }
}
