package com.srt.FreelanceMarketplace.service.application.messaging;

import com.srt.FreelanceMarketplace.domain.dto.MessageEventDTO;
import com.srt.FreelanceMarketplace.domain.entities.message.MessageEventEntity;
import com.srt.FreelanceMarketplace.mapper.MessageMapper;
import com.srt.FreelanceMarketplace.repository.messaging.MessageEventRepository;
import com.srt.FreelanceMarketplace.service.infrastructure.MessagingEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MessageEventService {
    @Autowired
    private MessageEventRepository repository;
    @Autowired
    private MessageMapper mapper;

    @Autowired
    @Value("${message.polling.timeout-sec}")
    private long timeoutSec;

    @Autowired
    private MessagingEventService messagingEventService;

    public DeferredResult<List<MessageEventDTO>> getEvents(
            UUID conversationId, Long lastEventId) {
        DeferredResult<List<MessageEventDTO>> result =
                new DeferredResult<>(timeoutSec * 1000);

        List<MessageEventEntity> events = repository.findByMessage_conversation_idAndIdGreaterThan(
                conversationId, lastEventId);
        if (!events.isEmpty()) {
            log.info("event received from db");

            result.setResult(events.stream()
                    .map(mapper::fromEventEntity)
                    .toList());
            return result;
        }

        messagingEventService.subscribe(result, conversationId);
        result.onTimeout(() -> result.setResult(List.of()));

        return result;
    }
}
