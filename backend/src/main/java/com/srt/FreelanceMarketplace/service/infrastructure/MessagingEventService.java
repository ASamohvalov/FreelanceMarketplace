package com.srt.FreelanceMarketplace.service.infrastructure;

import com.srt.FreelanceMarketplace.domain.dto.MessageEventDTO;
import com.srt.FreelanceMarketplace.domain.entities.message.MessageEventEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.UUID;

public interface MessagingEventService {
    void subscribe(DeferredResult<List<MessageEventDTO>> deferredResult, UUID conversationId);

    /**
     * @param event with message.conversationId required
     */
    void publishEvent(MessageEventEntity event);
}
