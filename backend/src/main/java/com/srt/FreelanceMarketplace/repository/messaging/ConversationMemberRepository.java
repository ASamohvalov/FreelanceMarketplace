package com.srt.FreelanceMarketplace.repository.messaging;

import com.srt.FreelanceMarketplace.domain.entities.message.ConversationMemberEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConversationMemberRepository extends JpaRepository<ConversationMemberEntity, UUID> {
    @Query("""
            select cm from ConversationMemberEntity cm
            join fetch cm.member m
            join fetch cm.conversation c
            where m != :member
            and c in (
                select sub.conversation
                from ConversationMemberEntity sub
                where sub.member = :member
            )
            """)
    List<ConversationMemberEntity>  findAllConversationsByMember(UserEntity member);
}
