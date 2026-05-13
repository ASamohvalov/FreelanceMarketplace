package com.srt.FreelanceMarketplace.service.infrastructure;

import com.srt.FreelanceMarketplace.domain.entities.feedback.FeedbackConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.ProposalEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderExtensionEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderReportEntity;
import com.srt.FreelanceMarketplace.domain.entities.payment.TransferEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;

public interface NotificationSenderService {
    /**
     * @param order order.serviceEntity NOT NULL!
     */
    void sendNewOrder(OrderEntity order, UserEntity recipient, UserEntity sender);

    /**
     * @param proposal proposal.serviceEntity NOT NULL!
     */
    void sendNewProposal(ProposalEntity proposal, UserEntity recipient, UserEntity sender);

    /**
     * @param report report.order.serviceEntity NOT NULL!
     */
    void sendNewOrderReport(OrderReportEntity report, UserEntity recipient, UserEntity sender);

    /**
     * @param order order.serviceEntity NOT NULL!
     */
    void sendOrderCompleted(OrderEntity order, UserEntity recipient, UserEntity sender);

    /**
     * @param report report.order.serviceEntity NOT NULL!
     */
    void sendReportRejected(OrderReportEntity report, UserEntity recipient, UserEntity sender);

    void sendMoneyTransferred(TransferEntity transfer, UserEntity recipient, UserEntity sender);

    void sendOrderCancelled(OrderEntity order, UserEntity recipient, UserEntity sender);

    void sendOrderAccepted(OrderEntity order, UserEntity recipient, UserEntity sender);

    /**
     * @param orderExtension order.serviceEntity NOT NULL!
     */
    void sendOrderExtendDeadlineRequest(OrderExtensionEntity orderExtension, UserEntity recipient, UserEntity sender);

    /**
     * @param orderExtension order.serviceEntity NOT NULL!
     */
    void sendOrderExtensionAccepted(OrderExtensionEntity orderExtension, UserEntity recipient, UserEntity sender);

    /**
     * @param orderExtension order.serviceEntity NOT NULL!
     */
    void sendOrderExtensionRejected(OrderExtensionEntity orderExtension, UserEntity recipient, UserEntity sender);

    void sendFeedbackConversationOpened(FeedbackConversationEntity feedbackConversation, UserEntity recipient, UserEntity sender);
}
