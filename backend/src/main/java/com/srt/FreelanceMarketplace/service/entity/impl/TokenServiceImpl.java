package com.srt.FreelanceMarketplace.service.entity.impl;

import com.srt.FreelanceMarketplace.domain.entities.TokenEntity;
import com.srt.FreelanceMarketplace.domain.entities.UserEntity;
import com.srt.FreelanceMarketplace.repository.TokenRepository;
import com.srt.FreelanceMarketplace.service.entity.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Override
    public void save(TokenEntity entity) {
        tokenRepository.save(entity);
    }

    @Override
    public void deleteByUserAndToken(UserEntity user, String token) {
        tokenRepository.deleteByUserAndToken(user, token);
    }
}
