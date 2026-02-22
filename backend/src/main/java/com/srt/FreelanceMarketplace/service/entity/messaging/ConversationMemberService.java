package com.srt.FreelanceMarketplace.service.entity.messaging;

import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.ConversationMemberEntity;
import com.srt.FreelanceMarketplace.repository.messaging.ConversationMemberRepository;
import com.srt.FreelanceMarketplace.service.logic.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationMemberService {
    private final ConversationMemberRepository repository;

    private final AuthHelperService authHelperService;

    public void save(ConversationMemberEntity conversationMember) {
        repository.save(conversationMember);
    }

    public ConversationMemberEntity findMemberByConversation(ConversationEntity conversation) {
        UUID userId = authHelperService.getUser().getId();
        return repository.findByConversationWithMember(conversation).stream()
                .filter((cm) -> cm.getMember().getId() != userId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("no member found, server error"));
    }
}
