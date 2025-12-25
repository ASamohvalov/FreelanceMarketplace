package com.srt.FreelanceMarketplace.service.entity;

import com.srt.FreelanceMarketplace.domain.entities.TokenEntity;
import com.srt.FreelanceMarketplace.domain.entities.UserEntity;

public interface TokenService {
    void save(TokenEntity entity);
    void deleteByUserAndToken(UserEntity user, String token);
}
