package com.srt.FreelanceMarketplace.repository.messaging;

import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.MessageEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {
    List<MessageEntity> findAllByConversationOrderBySendAtAsc(ConversationEntity conversation);

    @Query("""
            select m from MessageEntity m
            join fetch m.author a
            join m.conversation c
            where c = :conversation
            and m.sendAt > :date
            and a <> :author
            order by m.sendAt asc
            """)
    List<MessageEntity> findAllUnreadMessagesAfterDate(
            ConversationEntity conversation, Instant date, UserEntity author
    );

    @Transactional
    @Modifying
    @Query("update MessageEntity m set m.isRead = true where m.id = :id")
    void updateReadById(UUID id);
}
