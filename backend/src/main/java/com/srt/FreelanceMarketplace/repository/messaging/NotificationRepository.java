package com.srt.FreelanceMarketplace.repository.messaging;

import com.srt.FreelanceMarketplace.domain.entities.message.NotificationEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {
    List<NotificationEntity> findAllByRecipient(UserEntity recipient);
}
