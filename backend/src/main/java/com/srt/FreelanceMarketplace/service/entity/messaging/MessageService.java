package com.srt.FreelanceMarketplace.service.entity.messaging;

import com.srt.FreelanceMarketplace.domain.entities.messages.MessageEntity;
import com.srt.FreelanceMarketplace.domain.messaging.NewMessageRequest;
import com.srt.FreelanceMarketplace.repository.messaging.MessageRepository;
import com.srt.FreelanceMarketplace.service.logic.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repository;

    private final ConversationService conversationService;
    private final AuthHelperService authHelperService;

    public void sendMessage(NewMessageRequest request) {
        MessageEntity message = MessageEntity.builder()
                .conversation(conversationService.getById(request.getConversationId()))
                .author(authHelperService.getUser())
                .message(request.getMessage())
                .build();
        repository.save(message);
    }
}
