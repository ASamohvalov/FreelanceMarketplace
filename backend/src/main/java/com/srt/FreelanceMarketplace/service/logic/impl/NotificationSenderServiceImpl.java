package com.srt.FreelanceMarketplace.service.logic.impl;

import com.srt.FreelanceMarketplace.domain.dto.NotificationTypeEnum;
import com.srt.FreelanceMarketplace.domain.entities.message.NotificationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.ProposalEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.repository.messaging.NotificationRepository;
import com.srt.FreelanceMarketplace.service.logic.NotificationSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class NotificationSenderServiceImpl implements NotificationSenderService {
    private final NotificationRepository repository;

    @Override
    public void sendNewOrder(OrderEntity order, UserEntity recipient, UserEntity sender) {
        NotificationEntity notification = NotificationEntity.builder()
                .title("Новый заказ")
                .message(String.format("%s %s заказал у вас услугу - %s", sender.getFirstName(), sender.getLastName(), order.getService().getTitle()))
                .type(NotificationTypeEnum.NEW_ORDER)
                .recipient(recipient)
                .entityType(getEntityType(order))
                .entityId(order.getId())
                .sendAt(Instant.now())
                .build();
        repository.save(notification);
    }

    @Override
    public void sendNewProposal(ProposalEntity proposal, UserEntity recipient, UserEntity sender) {
        NotificationEntity notification = NotificationEntity.builder()
                .title("Зарос на обсуждение")
                .message(String.format("%s %s хочет обсудить с вами услугу - %s", sender.getFirstName(), sender.getLastName(), proposal.getService().getTitle()))
                .type(NotificationTypeEnum.NEW_PROPOSAL)
                .recipient(recipient)
                .entityType(getEntityType(proposal))
                .entityId(proposal.getId())
                .sendAt(Instant.now())
                .build();
        repository.save(notification);
    }

    private String getEntityType(Object entity) {
        if (entity instanceof OrderEntity) {
            return "orders";
        }
        if (entity instanceof ProposalEntity) {
            return "proposals";
        }
        throw new IllegalStateException("no such entity type");
    }
}
