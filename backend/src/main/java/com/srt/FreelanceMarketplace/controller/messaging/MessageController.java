package com.srt.FreelanceMarketplace.controller.messaging;

import com.srt.FreelanceMarketplace.domain.messaging.NewMessageRequest;
import com.srt.FreelanceMarketplace.service.entity.messaging.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messaging")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    // todo TEST
    @PostMapping("/send")
    public void sendMessage(@RequestBody @Valid NewMessageRequest request) {
        messageService.sendMessage(request);
    }
}
