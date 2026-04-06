package com.srt.FreelanceMarketplace.service.application.messaging;

import com.srt.FreelanceMarketplace.domain.dto.response.messaging.ConversationContextResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.messaging.ConversationResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.messaging.MessageResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.messaging.NewMessageRequest;
import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.MessageEntity;
import com.srt.FreelanceMarketplace.mapper.ConversationMapper;
import com.srt.FreelanceMarketplace.mapper.MessageMapper;
import com.srt.FreelanceMarketplace.mapper.OrderMapper;
import com.srt.FreelanceMarketplace.repository.messaging.MessageRepository;
import com.srt.FreelanceMarketplace.service.domain.messaging.ConversationDomainService;
import com.srt.FreelanceMarketplace.service.domain.messaging.ConversationMemberDomainService;
import com.srt.FreelanceMarketplace.service.domain.service.ServiceDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repository;
    private final MessageMapper mapper;

    private final ConversationDomainService conversationDomainService;
    private final ConversationMemberDomainService conversationMemberDomainService;
    private final AuthHelperService authHelperService;
    private final ConversationMapper conversationMapper;
    private final OrderMapper orderMapper;
    private final ServiceDomainService serviceDomainService;

    public void sendMessage(NewMessageRequest request) {
        MessageEntity message = MessageEntity.builder()
                .conversation(conversationDomainService.getById(request.getConversationId()))
                .author(authHelperService.getUser())
                .message(request.getMessage())
                .build();
        repository.save(message);
    }

    public List<MessageResponse> getMessages(UUID conversationId, Instant after) {
        conversationDomainService.throwIfNotExistsById(conversationId);
        ConversationEntity conversation = conversationDomainService.getReferenceById(conversationId);

        if (after != null) {
            return repository.findAllUnreadMessagesAfterDate(
                            conversation, after, authHelperService.getUser()
                    ).stream()
                    .map(mapper::fromEntity)
                    .toList();
        }
        return repository.findAllByConversationOrderBySendAtAsc(conversation).stream()
                .map(mapper::fromEntity)
                .toList();
    }

    public void readMessages(List<UUID> messages) {
        messages.forEach(repository::updateReadById);
    }

    public List<ConversationResponse> getAllConversations() {
        return conversationMemberDomainService.findAllByMemberWithMember(authHelperService.getUser()).stream()
                .map(conversationMapper::fromConversationMemberToResponse)
                .toList();
    }

    public ConversationContextResponse getServiceByConversationId(UUID conversationId) {
        ConversationEntity conversation = conversationDomainService.getByIdWithOrderAndServiceAndFreelancer(conversationId);
        return new ConversationContextResponse(
                orderMapper.toResponse(conversation.getOrder()),
                serviceDomainService.mapToServiceResponse(conversation.getService()),
                conversation.getType()
        );
    }
}
