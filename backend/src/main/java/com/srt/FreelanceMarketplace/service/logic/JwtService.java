package com.srt.FreelanceMarketplace.service.logic;

import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;

public interface JwtService {
    String generateAccessToken(UserEntity user);
    String generateRefreshToken(UserEntity user);

    boolean validateAccessToken(String token);

    String getSubject(String token);
}
