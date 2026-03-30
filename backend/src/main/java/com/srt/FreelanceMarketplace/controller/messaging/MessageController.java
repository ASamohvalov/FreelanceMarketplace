package com.srt.FreelanceMarketplace.controller.messaging;

import com.srt.FreelanceMarketplace.domain.dto.response.messaging.ConversationContextResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.messaging.ConversationResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.messaging.MessageResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.messaging.NewMessageRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.order.OrderServiceInfoResponse;
import com.srt.FreelanceMarketplace.service.application.messaging.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/messaging")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send")
    public void sendMessage(@RequestBody @Valid NewMessageRequest request) {
        messageService.sendMessage(request);
    }

    @GetMapping("/message/get/{conversationId}")
    public List<MessageResponse> getMessages(
            @PathVariable UUID conversationId,
            @RequestParam(value = "after", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant after
    ) {
        return messageService.getMessages(conversationId, after);
    }

    @GetMapping("/conversation/personal/get_all")
    public List<ConversationResponse> getAllConversations() {
        return messageService.getAllConversations();
    }

    @GetMapping("/conversation/{conversationId}/context")
    public ConversationContextResponse getConversationContext(@PathVariable UUID conversationId) {
        return messageService.getServiceByConversationId(conversationId);
    }

    @PatchMapping("/message/read")
    public void readMessages(@RequestBody List<UUID> messages) {
        messageService.readMessages(messages);
    }
}
