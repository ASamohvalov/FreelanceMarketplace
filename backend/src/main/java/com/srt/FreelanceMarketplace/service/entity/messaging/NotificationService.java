package com.srt.FreelanceMarketplace.service.entity.messaging;

import com.srt.FreelanceMarketplace.domain.dto.NotificationTypeEnum;
import com.srt.FreelanceMarketplace.domain.dto.response.messaging.NotificationResponse;
import com.srt.FreelanceMarketplace.domain.entities.message.NotificationEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.NotificationMapper;
import com.srt.FreelanceMarketplace.repository.messaging.NotificationRepository;
import com.srt.FreelanceMarketplace.service.logic.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository repository;
    private final NotificationMapper mapper;

    private final AuthHelperService authHelperService;
    private final ProposalService proposalService;

    public List<NotificationResponse> getAllPersonal() {
        return repository.findAllByRecipientOrderBySendAtDesc(authHelperService.getUser()).stream()
                .filter((n) -> !n.isHide())
                .map((n) -> {
                    var res = mapper.toResponse(n);
                    if (res.getType() == NotificationTypeEnum.NEW_PROPOSAL) {
                        res.setData(
                                Map.of("message", proposalService.getById(res.getEntityId()).getDescription())
                        );
                    }
                    return res;
                })
                .toList();
    }

    public List<NotificationResponse> getAllPersonalWithHidden() {
        return repository.findAllByRecipientOrderBySendAtDesc(authHelperService.getUser()).stream()
                .map((n) -> {
                    var res = mapper.toResponse(n);
                    if (res.getType() == NotificationTypeEnum.NEW_PROPOSAL) {
                        res.setData(
                                Map.of("message", proposalService.getById(res.getEntityId()).getDescription())
                        );
                    }
                    return res;
                })
                .toList();
    }

    public void hide(UUID id) {
        NotificationEntity notification = getById(id);
        notification.setHide(true);
        repository.save(notification);
    }

    private NotificationEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new GlobalBadRequestException("notification with this id not found"));
    }
}
