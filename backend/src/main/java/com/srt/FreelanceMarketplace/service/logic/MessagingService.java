package com.srt.FreelanceMarketplace.service.logic;

import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.messages.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;

public interface MessagingService {
    ConversationEntity createConversation(FreelancerEntity freelancer, UserEntity user);
}
