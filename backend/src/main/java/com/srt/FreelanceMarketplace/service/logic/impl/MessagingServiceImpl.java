package com.srt.FreelanceMarketplace.service.logic.impl;

import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.messages.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.messages.ConversationMemberEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.service.entity.messaging.ConversationMemberService;
import com.srt.FreelanceMarketplace.service.entity.messaging.ConversationService;
import com.srt.FreelanceMarketplace.service.logic.MessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessagingServiceImpl implements MessagingService {
    private final ConversationService conversationService;
    private final ConversationMemberService conversationMemberService;

    @Override
    public ConversationEntity createConversation(FreelancerEntity freelancer, UserEntity user) {
        ConversationEntity conversation = new ConversationEntity();
        conversationService.save(conversation);

        conversationMemberService.save(new ConversationMemberEntity(freelancer.getUser(), conversation));
        conversationMemberService.save(new ConversationMemberEntity(user, conversation));

        return conversation;
    }
}
