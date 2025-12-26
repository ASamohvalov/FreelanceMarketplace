package com.srt.FreelanceMarketplace.service.entity;

import com.srt.FreelanceMarketplace.domain.entities.TokenEntity;

public interface TokenService {
    void save(TokenEntity entity);
    void deleteByToken(String token);
}
