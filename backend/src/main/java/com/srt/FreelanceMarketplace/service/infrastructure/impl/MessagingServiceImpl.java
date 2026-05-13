package com.srt.FreelanceMarketplace.service.infrastructure.impl;

import com.srt.FreelanceMarketplace.domain.dto.typeEnum.ConversationTypeEnum;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.ConversationMemberEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.service.domain.messaging.ConversationMemberDomainService;
import com.srt.FreelanceMarketplace.service.domain.messaging.ConversationDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.MessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessagingServiceImpl implements MessagingService {
    private final ConversationDomainService conversationDomainService;
    private final ConversationMemberDomainService conversationMemberDomainService;

    @Override
    public ConversationEntity createConversation(
            FreelancerEntity freelancer,
            UserEntity user,
            ServiceEntity service) {
        ConversationEntity conversation = new ConversationEntity();
        conversation.setService(service);
        conversation.setType(ConversationTypeEnum.DISCUSSION);
        conversationDomainService.save(conversation);

        conversationMemberDomainService.save(new ConversationMemberEntity(freelancer.getUser(), conversation));
        conversationMemberDomainService.save(new ConversationMemberEntity(user, conversation));

        return conversation;
    }

    @Override
    public ConversationEntity createConversation(
            FreelancerEntity freelancer,
            UserEntity user,
            ServiceEntity service,
            OrderEntity order) {
        ConversationEntity conversation = new ConversationEntity();
        conversation.setService(service);
        conversation.setOrder(order);
        conversation.setType(ConversationTypeEnum.ORDER);
        conversationDomainService.save(conversation);

        conversationMemberDomainService.save(new ConversationMemberEntity(freelancer.getUser(), conversation));
        conversationMemberDomainService.save(new ConversationMemberEntity(user, conversation));

        return conversation;
    }

    @Override
    public ConversationEntity createConversation(UserEntity moderator, UserEntity user) {
        ConversationEntity conversation = new ConversationEntity();
        conversation.setType(ConversationTypeEnum.FEEDBACK);
        conversationDomainService.save(conversation);

        conversationMemberDomainService.save(new ConversationMemberEntity(moderator, conversation));
        conversationMemberDomainService.save(new ConversationMemberEntity(user, conversation));

        return conversation;
    }

    @Override
    public boolean isConversationExists(FreelancerEntity freelancer, UserEntity user) {
        return conversationMemberDomainService.existsByMembers(freelancer.getUser(), user);
    }

    @Override
    public void changeConversationType(
            FreelancerEntity freelancer,
            UserEntity user,
            ConversationTypeEnum type,
            OrderEntity order) {
        ConversationEntity conversation = conversationDomainService.getByMemberIds(
                freelancer.getUser().getId(),
                user.getId()
        );
        conversation.setType(type);
        conversation.setOrder(order);
        conversationDomainService.save(conversation);
    }
}
