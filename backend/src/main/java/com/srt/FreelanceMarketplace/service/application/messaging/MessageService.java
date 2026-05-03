package com.srt.FreelanceMarketplace.service.application.messaging;

import com.srt.FreelanceMarketplace.domain.dto.MessageEventTypeEnum;
import com.srt.FreelanceMarketplace.domain.dto.request.messaging.EditMessageRequest;
import com.srt.FreelanceMarketplace.domain.dto.request.messaging.NewMessageRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.messaging.ConversationContextResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.messaging.ConversationResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.messaging.MessageListResponse;
import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.MessageEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.MessageEventEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.ConversationMapper;
import com.srt.FreelanceMarketplace.mapper.MessageMapper;
import com.srt.FreelanceMarketplace.mapper.OrderMapper;
import com.srt.FreelanceMarketplace.repository.messaging.MessageRepository;
import com.srt.FreelanceMarketplace.service.domain.messaging.ConversationDomainService;
import com.srt.FreelanceMarketplace.service.domain.messaging.ConversationMemberDomainService;
import com.srt.FreelanceMarketplace.service.domain.messaging.MessageDomainService;
import com.srt.FreelanceMarketplace.service.domain.messaging.MessageEventDomainService;
import com.srt.FreelanceMarketplace.service.domain.service.ServiceDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import com.srt.FreelanceMarketplace.service.infrastructure.MessagingEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repository;
    private final MessageMapper mapper;
    private final MessageDomainService domainService;

    private final ConversationDomainService conversationDomainService;
    private final ConversationMemberDomainService conversationMemberDomainService;
    private final AuthHelperService authHelperService;
    private final ConversationMapper conversationMapper;
    private final OrderMapper orderMapper;
    private final ServiceDomainService serviceDomainService;
    private final MessagingEventService messagingEventService;
    private final MessageEventDomainService messageEventDomainService;

    public Map<String, UUID> sendMessage(NewMessageRequest request) {
        MessageEntity message = MessageEntity.builder()
                .conversation(conversationDomainService.getById(request.getConversationId()))
                .author(authHelperService.getUser())
                .message(request.getMessage())
                .build();
        repository.save(message);

        messagingEventService.publishEvent(MessageEventEntity.builder()
                .message(message)
                .type(MessageEventTypeEnum.NEW_MESSAGE)
                .build());

        return Map.of("id", message.getId());
    }

    public MessageListResponse getMessages(UUID conversationId, Instant after) {
        conversationDomainService.throwIfNotExistsById(conversationId);
        ConversationEntity conversation = conversationDomainService.getReferenceById(conversationId);

        if (after != null) {
            return new MessageListResponse(
                    repository.findAllUnreadMessagesAfterDate(
                                    conversation, after, authHelperService.getUser()
                            ).stream()
                            .map(mapper::fromEntity)
                            .toList(),
                    messageEventDomainService.findLastId(conversation)
            );
        }
        return new MessageListResponse(
                repository.findAllByConversationAndDeletedFalseOrderBySendAtAsc(conversation).stream()
                        .map(mapper::fromEntity)
                        .toList(),
                messageEventDomainService.findLastId(conversation)
        );
    }

    public void readMessages(List<UUID> messages) {
        List<MessageEventEntity> eventEntityList = new ArrayList<>();
        for (UUID messageId : messages) {
            MessageEntity message = domainService.getById(messageId);
            repository.updateRead(message);

            MessageEventEntity event = MessageEventEntity.builder()
                    .message(message)
                    .type(MessageEventTypeEnum.READ_MESSAGE)
                    .build();
            messagingEventService.publishEvent(event);

            eventEntityList.add(event);
        }
        messageEventDomainService.saveAll(eventEntityList);
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

    public void deleteMessage(UUID messageId) {
        MessageEntity message = domainService.getById(messageId);
        if (!message.getAuthor().getId()
                .equals(authHelperService.getUser().getId())) {
            throw new GlobalBadRequestException("the user cannot delete a message that is not his own");
        }

        message.setDeleted(true);
        message.setUpdateAt(Instant.now());
        repository.save(message);

        messagingEventService.publishEvent(MessageEventEntity.builder()
                .message(message)
                .type(MessageEventTypeEnum.DELETE_MESSAGE)
                .build());
    }

    public void editMessage(EditMessageRequest request) {
        MessageEntity message = domainService.getById(request.getId());
        if (!message.getAuthor().getId()
                .equals(authHelperService.getUser().getId())) {
            throw new GlobalBadRequestException("the user cannot delete a message that is not his own");
        }
        message.setMessage(request.getMessage());
        message.setUpdateAt(Instant.now());
        repository.save(message);

        messagingEventService.publishEvent(MessageEventEntity.builder()
                .message(message)
                .type(MessageEventTypeEnum.EDIT_MESSAGE)
                .build());
    }
}
