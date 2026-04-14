package com.srt.FreelanceMarketplace.service.infrastructure.impl;

import com.srt.FreelanceMarketplace.domain.dto.NotificationTypeEnum;
import com.srt.FreelanceMarketplace.domain.entities.message.NotificationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.ProposalEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderReportEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.repository.messaging.NotificationRepository;
import com.srt.FreelanceMarketplace.service.infrastructure.NotificationSenderService;
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
                .build();
        repository.save(notification);
    }

    @Override
    public void sendNewOrderReport(OrderReportEntity report, UserEntity recipient, UserEntity sender) {
        NotificationEntity notification = NotificationEntity.builder()
                .title("Отчёт по заказу")
                .message(String.format("%s %s отправил отчёт по заказу - %s", sender.getFirstName(), sender.getLastName(), report.getOrder().getService().getTitle()))
                .type(NotificationTypeEnum.NEW_ORDER_REPORT)
                .recipient(recipient)
                .entityType(getEntityType(report))
                .entityId(report.getId())
                .build();
        repository.save(notification);
    }

    @Override
    public void sendOrderCompleted(OrderEntity order, UserEntity recipient, UserEntity sender) {
        NotificationEntity notification = NotificationEntity.builder()
                .title("Заказ завершен")
                .message(String.format("%s %s принял ваш отчёт по заказу - %s", sender.getFirstName(), sender.getLastName(), order.getService().getTitle()))
                .type(NotificationTypeEnum.ORDER_COMPLETED)
                .recipient(recipient)
                .entityType(getEntityType(order))
                .entityId(order.getId())
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
        if (entity instanceof OrderReportEntity) {
            return "order_reports";
        }
        throw new IllegalStateException("no such entity type");
    }
}
