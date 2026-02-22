package com.srt.FreelanceMarketplace.service.entity.messaging;

import com.srt.FreelanceMarketplace.domain.dto.response.messaging.ConversationResponse;
import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.UserMapper;
import com.srt.FreelanceMarketplace.repository.messaging.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationService {
    private final ConversationRepository repository;

    private final ConversationMemberService conversationMemberService;
    private final UserMapper userMapper;

    public void save(ConversationEntity conversation) {
        repository.save(conversation);
    }

    public void throwIfNotExistsById(UUID id) {
        if (!repository.existsById(id)) {
            throw new GlobalBadRequestException("such conversation not found");
        }
    }

    public ConversationEntity getReferenceById(UUID id) {
        return repository.getReferenceById(id);
    }

    public ConversationEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new GlobalBadRequestException("such conversation not found"));
    }

    public List<ConversationResponse> getAllConversations() {
        return repository.findAll().stream()
                .map(c ->
                    new ConversationResponse(
                            c.getId(),
                            userMapper.entityToUserNameResponse(
                                conversationMemberService.findMemberByConversation(c).getMember()
                            )
                    ))
                .toList();
    }
}
