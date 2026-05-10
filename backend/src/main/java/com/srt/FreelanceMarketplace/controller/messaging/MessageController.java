package com.srt.FreelanceMarketplace.controller.messaging;

import com.srt.FreelanceMarketplace.domain.dto.request.messaging.EditMessageRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.messaging.*;
import com.srt.FreelanceMarketplace.domain.dto.request.messaging.NewMessageRequest;
import com.srt.FreelanceMarketplace.service.application.messaging.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/messaging")
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send")
    public Map<String, UUID> sendMessage(@RequestBody @Valid NewMessageRequest request) {
        return messageService.sendMessage(request);
    }

    @GetMapping("/message/get/{conversationId}")
    public MessageListResponse getMessages(
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

    @DeleteMapping("/message/delete/{messageId}")
    public void deleteMessage(@PathVariable UUID messageId) {
        messageService.deleteMessage(messageId);
    }

    @PutMapping("/message/edit")
    public void editMessage(@RequestBody @Valid EditMessageRequest request) {
        messageService.editMessage(request);
    }

    @GetMapping("/conversation/exists")
    public ConversationExistsResponse checkConversationExists(
            @RequestParam UUID userId) {
        return messageService.checkConversationExists(userId);
    }
}
