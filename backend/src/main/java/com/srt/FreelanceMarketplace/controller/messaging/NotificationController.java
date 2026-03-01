package com.srt.FreelanceMarketplace.controller.messaging;

import com.srt.FreelanceMarketplace.domain.dto.response.messaging.NotificationResponse;
import com.srt.FreelanceMarketplace.service.entity.messaging.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/get_all_personal")
    public List<NotificationResponse> getAllPersonal() {
        return notificationService.getAllPersonal();
    }
}
