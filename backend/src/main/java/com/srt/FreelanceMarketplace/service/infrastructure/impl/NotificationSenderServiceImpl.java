package com.srt.FreelanceMarketplace.service.infrastructure.impl;

import com.srt.FreelanceMarketplace.domain.dto.typeEnum.NotificationTypeEnum;
import com.srt.FreelanceMarketplace.domain.entities.feedback.FeedbackConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.NotificationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.ProposalEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderExtensionEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderReportEntity;
import com.srt.FreelanceMarketplace.domain.entities.payment.TransferEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.repository.messaging.NotificationRepository;
import com.srt.FreelanceMarketplace.service.infrastructure.CommissionService;
import com.srt.FreelanceMarketplace.service.infrastructure.NotificationSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationSenderServiceImpl implements NotificationSenderService {
    private final NotificationRepository repository;

    private final CommissionService commissionService;

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
                .message(String.format("%s %s отправил отчёт по заказу: \"%s\"", sender.getFirstName(), sender.getLastName(), report.getOrder().getService().getTitle()))
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
                .message(String.format("%s %s завершил заказ: \"%s\"", sender.getFirstName(), sender.getLastName(), order.getService().getTitle()))
                .type(NotificationTypeEnum.ORDER_COMPLETED)
                .recipient(recipient)
                .entityType(getEntityType(order))
                .entityId(order.getId())
                .build();
        repository.save(notification);
    }

    @Override
    public void sendReportRejected(OrderReportEntity report, UserEntity recipient, UserEntity sender) {
        NotificationEntity notification = NotificationEntity.builder()
                .title("Отчёт отклонен")
                .message(String.format(
                        "%s %s отклонил ваш отчёт: \"%s\", по заказу: \"%s\"",
                        sender.getFirstName(),
                        sender.getLastName(),
                        report.getTitle(),
                        report.getOrder().getService().getTitle()))
                .type(NotificationTypeEnum.ORDER_COMPLETED)
                .recipient(recipient)
                .entityType(getEntityType(report))
                .entityId(report.getId())
                .build();
        repository.save(notification);
    }

    @Override
    public void sendMoneyTransferred(TransferEntity transfer, UserEntity recipient, UserEntity sender) {
        NotificationEntity notification = NotificationEntity.builder()
                .title("Перевод денег")
                .message(String.format(
                        "Получен перевод от %s %s, сумма: %s р",
                        sender.getFirstName(),
                        sender.getLastName(),
                        commissionService.getPriceWithoutCommission(transfer.getAmount())))
                .type(NotificationTypeEnum.MONEY_TRANSFERRED)
                .recipient(recipient)
                .entityType(getEntityType(transfer))
                .entityId(transfer.getId())
                .build();
        repository.save(notification);
    }

    @Override
    public void sendOrderCancelled(OrderEntity order, UserEntity recipient, UserEntity sender) {
        NotificationEntity notification = NotificationEntity.builder()
                .title("Отмена заказа")
                .message(String.format(
                        "%s %s не принял ваш заказ",
                        sender.getFirstName(),
                        sender.getLastName()))
                .type(NotificationTypeEnum.ORDER_CANCELLED)
                .recipient(recipient)
                .entityType(getEntityType(order))
                .entityId(order.getId())
                .build();
        repository.save(notification);
    }

    @Override
    public void sendOrderAccepted(OrderEntity order, UserEntity recipient, UserEntity sender) {
        NotificationEntity notification = NotificationEntity.builder()
                .title("Принятие заказа")
                .message(String.format(
                        "%s %s принял ваш заказ и приступает к работе",
                        sender.getFirstName(),
                        sender.getLastName()))
                .type(NotificationTypeEnum.ORDER_ACCEPTED)
                .recipient(recipient)
                .entityType(getEntityType(order))
                .entityId(order.getId())
                .build();
        repository.save(notification);
    }

    @Override
    public void sendOrderExtendDeadlineRequest(OrderExtensionEntity orderExtension, UserEntity recipient, UserEntity sender) {
        NotificationEntity notification = NotificationEntity.builder()
                .title("Запрос на продление дедлайна")
                .message(String.format(
                        "%s %s запросил у вас увеличить скрок завершения заказа: \"%s\" на %d дней",
                        sender.getFirstName(),
                        sender.getLastName(),
                        orderExtension.getOrder().getService().getTitle(),
                        orderExtension.getDaysAdded()))
                .type(NotificationTypeEnum.EXTEND_ORDER_DEADLINE_REQUEST)
                .recipient(recipient)
                .entityType(getEntityType(orderExtension))
                .entityId(orderExtension.getId())
                .build();
        repository.save(notification);
    }

    @Override
    public void sendOrderExtensionAccepted(OrderExtensionEntity orderExtension, UserEntity recipient, UserEntity sender) {
        NotificationEntity notification = NotificationEntity.builder()
                .title("Ответ на запрос о продлении дедлайна")
                .message(String.format(
                        "%s %s продлил дедлайн на заказ \"%s\"",
                        sender.getFirstName(),
                        sender.getLastName(),
                        orderExtension.getOrder().getService().getTitle()))
                .type(NotificationTypeEnum.ORDER_EXTENSION_ACCEPTED)
                .recipient(recipient)
                .entityType(getEntityType(orderExtension))
                .entityId(orderExtension.getOrder().getId())
                .build();
        repository.save(notification);
    }

    @Override
    public void sendOrderExtensionRejected(OrderExtensionEntity orderExtension, UserEntity recipient, UserEntity sender) {
        NotificationEntity notification = NotificationEntity.builder()
                .title("Ответ на запрос о продлении дедлайна")
                .message(String.format(
                        "%s %s отказался продлить дедлайн на заказ \"%s\"",
                        sender.getFirstName(),
                        sender.getLastName(),
                        orderExtension.getOrder().getService().getTitle()))
                .type(NotificationTypeEnum.ORDER_EXTENSION_REJECTED)
                .recipient(recipient)
                .entityType(getEntityType(orderExtension))
                .entityId(orderExtension.getOrder().getId())
                .build();
        repository.save(notification);
    }

    @Override
    public void sendFeedbackConversationOpened(FeedbackConversationEntity feedbackConversation, UserEntity recipient, UserEntity sender) {
        NotificationEntity notification = NotificationEntity.builder()
                .title("Ответ на обратную связь")
                .message(String.format(
                        "%s %s открыл с вами чат по вопросу: \"%s\"",
                        sender.getFirstName(),
                        sender.getLastName(),
                        feedbackConversation.getFeedback().getTitle()))
                .type(NotificationTypeEnum.FEEDBACK_CONVERSATION_CREATED)
                .recipient(recipient)
                .entityType(getEntityType(feedbackConversation.getConversation()))
                .entityId(feedbackConversation.getConversation().getId())
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
        if (entity instanceof TransferEntity) {
            return "transfers";
        }
        if (entity instanceof OrderExtensionEntity) {
            return "order_extensions";
        }
        if (entity instanceof ConversationEntity) {
            return "conversations";
        }
        throw new IllegalStateException("no such entity type");
    }
}
