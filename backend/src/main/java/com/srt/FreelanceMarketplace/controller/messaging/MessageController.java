package com.srt.FreelanceMarketplace.controller.messaging;

import com.srt.FreelanceMarketplace.domain.dto.response.messaging.ConversationResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.messaging.MessageResponse;
import com.srt.FreelanceMarketplace.domain.messaging.NewMessageRequest;
import com.srt.FreelanceMarketplace.service.entity.messaging.ConversationService;
import com.srt.FreelanceMarketplace.service.entity.messaging.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/messaging")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final ConversationService conversationService;

    @PostMapping("/send")
    public void sendMessage(@RequestBody @Valid NewMessageRequest request) {
        messageService.sendMessage(request);
    }

    @GetMapping("/get_messages/{conversationId}")
    public List<MessageResponse> getMessages(@PathVariable UUID conversationId) {
        return messageService.getMessages(conversationId);
    }

    @GetMapping("/conversation/get_all")
    public List<ConversationResponse> getAllConversations() {
        return conversationService.getAllConversations();
    }
}
