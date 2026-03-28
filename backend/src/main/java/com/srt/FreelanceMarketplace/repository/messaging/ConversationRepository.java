package com.srt.FreelanceMarketplace.repository.messaging;

import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.ConversationEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConversationRepository extends JpaRepository<ConversationEntity, UUID> {
    boolean existsById(UUID id);

    @Query("""
        select c from ConversationEntity c
        join fetch c.service s
        join fetch s.freelancer
        left join fetch c.order
        where c.id = :id
        """)
    Optional<ConversationEntity> findByIdWithOrderAndServiceAndFreelancer(UUID id);

    @Query("""
            select c from ConversationEntity c
            where exists (
                select cm from ConversationMemberEntity cm
                where cm.member.id = :member1Id
                    and cm.conversation = c
            ) and exists (
                select cm from ConversationMemberEntity cm
                where cm.member.id = :member2Id
                    and cm.conversation = c
            )
            """)
    Optional<ConversationEntity> findByMemberIds(UUID member1Id, UUID member2Id);
}
