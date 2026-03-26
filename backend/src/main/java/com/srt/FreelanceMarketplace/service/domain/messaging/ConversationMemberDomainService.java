package com.srt.FreelanceMarketplace.service.domain.messaging;

import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.ConversationMemberEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.messaging.ConversationMemberRepository;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationMemberDomainService {
    private final ConversationMemberRepository repository;

    public void save(ConversationMemberEntity conversationMember) {
        repository.save(conversationMember);
    }
    
    public List<ConversationMemberEntity> findAllByMemberWithMember(UserEntity member) {
        return repository.findAllConversationsByMember(member);
    }

    public boolean existsByMembers(UserEntity member1, UserEntity member2) {
        return repository.existsByMembers(member1, member2);
    }
}
