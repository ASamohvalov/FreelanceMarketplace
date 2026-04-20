package com.srt.FreelanceMarketplace.service.infrastructure;

import com.srt.FreelanceMarketplace.domain.dto.ConversationTypeEnum;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;

public interface MessagingService {
    ConversationEntity createConversation(
            FreelancerEntity freelancer,
            UserEntity user,
            ServiceEntity service
    );

    ConversationEntity createConversation(
            FreelancerEntity freelancer,
            UserEntity user,
            ServiceEntity service,
            OrderEntity order
    );

    boolean isConversationExists(FreelancerEntity freelancer, UserEntity user);

    void changeConversationType(
            FreelancerEntity freelancer,
            UserEntity user,
            ConversationTypeEnum type,
            OrderEntity order
    );
}
