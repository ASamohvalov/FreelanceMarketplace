package com.srt.FreelanceMarketplace.service.entity.messaging;

import com.srt.FreelanceMarketplace.domain.entities.message.ConversationMemberEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.repository.messaging.ConversationMemberRepository;
import com.srt.FreelanceMarketplace.service.logic.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationMemberService {
    private final ConversationMemberRepository repository;

    private final AuthHelperService authHelperService;

    public void save(ConversationMemberEntity conversationMember) {
        repository.save(conversationMember);
    }
    
    public List<ConversationMemberEntity> findAllByMemberWithMember(UserEntity member) {
        return repository.findAllConversationsByMember(member);
    }
}
