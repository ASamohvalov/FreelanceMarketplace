package com.srt.FreelanceMarketplace.controller.messaging;

import com.srt.FreelanceMarketplace.domain.dto.MessageEventDTO;
import com.srt.FreelanceMarketplace.service.application.messaging.MessageEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/messaging/event")
@RequiredArgsConstructor
public class MessageEventController {
    private final MessageEventService messageEventService;

    @GetMapping("/get")
    public DeferredResult<List<MessageEventDTO>> getEvents(
            @RequestParam UUID conversationId,
            @RequestParam(required = false) Long lastEventId) {
        if (lastEventId == null) {
            lastEventId = 0L;
        }
        var res = messageEventService.getEvents(conversationId, lastEventId);
        return res;
    }
}
