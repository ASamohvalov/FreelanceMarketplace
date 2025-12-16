package com.srt.FreelanceExchange.service.logic;

import com.srt.FreelanceExchange.domain.entities.UserEntity;

public interface JwtService {
    String generateAccessToken(UserEntity user);
    String generateRefreshToken(UserEntity user);

    boolean validateAccessToken(String token);
}
