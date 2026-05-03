package com.srt.FreelanceMarketplace.service.infrastructure.impl;

import com.srt.FreelanceMarketplace.domain.dto.MessageEventDTO;
import com.srt.FreelanceMarketplace.domain.entities.message.MessageEventEntity;
import com.srt.FreelanceMarketplace.mapper.MessageMapper;
import com.srt.FreelanceMarketplace.repository.messaging.MessageEventRepository;
import com.srt.FreelanceMarketplace.service.infrastructure.MessagingEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class MessagingEventServiceImpl implements MessagingEventService {
    private final Map<UUID, List<DeferredResult<List<MessageEventDTO>>>> eventStorage = new ConcurrentHashMap<>();

    @Autowired
    private MessageEventRepository messageEventRepository;
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void subscribe(DeferredResult<List<MessageEventDTO>> deferredResult, UUID conversationId) {
        eventStorage.computeIfAbsent(conversationId, k -> new CopyOnWriteArrayList<>())
                .add(deferredResult);

        deferredResult.onCompletion(() -> remove(deferredResult, conversationId));
        deferredResult.onTimeout(() -> remove(deferredResult, conversationId));
        deferredResult.onError((e) -> remove(deferredResult, conversationId));
    }

    @Override
    public void publishEvent(MessageEventEntity event) {
        messageEventRepository.save(event);
        var conversationEvents = eventStorage.get(event.getMessage().getConversation().getId());

        if (conversationEvents == null) return;

        for (var deferredResultList : conversationEvents) {
            deferredResultList.setResult(List.of(messageMapper.fromEventEntity(event))); // call callback method
            remove(deferredResultList, event.getMessage().getConversation().getId());
        }
    }

    private void remove(DeferredResult<List<MessageEventDTO>> deferredResult, UUID conversationId) {
        var conversationEvents = eventStorage.get(conversationId);
        if (conversationEvents != null) {
            conversationEvents.remove(deferredResult);

            if (conversationEvents.isEmpty()) {
                eventStorage.remove(conversationId);
            }
        }
    }
}
