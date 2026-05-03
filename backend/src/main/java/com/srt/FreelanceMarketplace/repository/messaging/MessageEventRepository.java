package com.srt.FreelanceMarketplace.repository.messaging;

import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.MessageEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageEventRepository extends JpaRepository<MessageEventEntity, Long> {
    List<MessageEventEntity> findByMessage_conversation_idAndIdGreaterThan(UUID conversationId, Long id);

    Optional<MessageEventEntity> findFirstByMessage_conversationOrderByIdDesc(ConversationEntity conversation);
}
