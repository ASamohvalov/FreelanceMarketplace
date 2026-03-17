package com.srt.FreelanceMarketplace.service.infrastructure.impl;

import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.ConversationMemberEntity;
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
    public ConversationEntity createConversation(FreelancerEntity freelancer, UserEntity user) {
        ConversationEntity conversation = new ConversationEntity();
        conversationDomainService.save(conversation);

        conversationMemberDomainService.save(new ConversationMemberEntity(freelancer.getUser(), conversation));
        conversationMemberDomainService.save(new ConversationMemberEntity(user, conversation));

        return conversation;
    }
}
