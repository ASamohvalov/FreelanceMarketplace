package com.srt.FreelanceMarketplace.repository.messaging;

import com.srt.FreelanceMarketplace.domain.entities.messages.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.messages.ConversationMemberEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConversationMemberRepository extends JpaRepository<ConversationMemberEntity, UUID> {
    @EntityGraph(attributePaths = {"member"})
    @Query("select cm from ConversationMemberEntity cm")
    List<ConversationMemberEntity> findByConversationWithMember(ConversationEntity conversation);
}
