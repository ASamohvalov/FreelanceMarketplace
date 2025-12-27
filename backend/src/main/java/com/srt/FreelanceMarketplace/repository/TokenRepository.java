package com.srt.FreelanceMarketplace.repository;

import com.srt.FreelanceMarketplace.domain.entities.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, UUID> {
    void deleteByToken(String token);
    Optional<TokenEntity> findByToken(String token);
}
