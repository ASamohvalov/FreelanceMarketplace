package com.srt.FreelanceMarketplace.repository.payment;

import com.srt.FreelanceMarketplace.domain.entities.payment.AccountEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByUser(UserEntity user);
}
