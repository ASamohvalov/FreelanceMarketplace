package com.srt.FreelanceMarketplace.controller.messaging;

import com.srt.FreelanceMarketplace.domain.dto.response.messaging.NotificationResponse;
import com.srt.FreelanceMarketplace.service.entity.messaging.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/get_all_personal")
    public List<NotificationResponse> getAllPersonal() {
        return notificationService.getAllPersonal();
    }

    @GetMapping("/get_all_personal_with_hide")
    public List<NotificationResponse> getAllPersonalWithHidden() {
        return notificationService.getAllPersonalWithHidden();
    }

    @PostMapping("/hide/{notificationId}")
    public void hideNotification(@PathVariable UUID notificationId) {
        notificationService.hide(notificationId);
    }
}
