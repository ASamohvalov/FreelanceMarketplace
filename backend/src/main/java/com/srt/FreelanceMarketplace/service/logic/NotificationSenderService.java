package com.srt.FreelanceMarketplace.service.logic;

import com.srt.FreelanceMarketplace.domain.entities.message.ProposalEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
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
}
