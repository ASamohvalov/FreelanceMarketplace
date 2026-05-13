package com.srt.FreelanceMarketplace.repository.messaging;

import com.srt.FreelanceMarketplace.domain.entities.feedback.FeedbackConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FeedbackConversationRepository extends JpaRepository<FeedbackConversationEntity, UUID> {

    @EntityGraph(attributePaths = {"feedback"})
    Optional<FeedbackConversationEntity> findByConversation(ConversationEntity conversation);
}
