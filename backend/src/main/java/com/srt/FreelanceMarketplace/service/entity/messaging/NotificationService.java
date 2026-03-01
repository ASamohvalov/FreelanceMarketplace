package com.srt.FreelanceMarketplace.service.entity.messaging;

import com.srt.FreelanceMarketplace.domain.dto.response.messaging.NotificationResponse;
import com.srt.FreelanceMarketplace.mapper.NotificationMapper;
import com.srt.FreelanceMarketplace.repository.messaging.NotificationRepository;
import com.srt.FreelanceMarketplace.service.logic.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository repository;
    private final NotificationMapper mapper;

    private final AuthHelperService authHelperService;

    public List<NotificationResponse> getAllPersonal() {
        return repository.findAllByRecipient(authHelperService.getUser()).stream()
                .map(mapper::toResponse)
                .toList();
    }
}
