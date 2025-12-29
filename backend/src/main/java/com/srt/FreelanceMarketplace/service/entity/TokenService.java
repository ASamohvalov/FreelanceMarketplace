package com.srt.FreelanceMarketplace.service.entity;

import com.srt.FreelanceMarketplace.domain.entities.user.TokenEntity;

import java.util.Optional;

public interface TokenService {
    void save(TokenEntity entity);
    void deleteByToken(String token);
    Optional<TokenEntity> findByToken(String token);
}
