package com.srt.FreelanceMarketplace.service.entity.impl;

import com.srt.FreelanceMarketplace.domain.entities.TokenEntity;
import com.srt.FreelanceMarketplace.repository.TokenRepository;
import com.srt.FreelanceMarketplace.service.entity.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Override
    public void save(TokenEntity entity) {
        tokenRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteByToken(String token) {
        tokenRepository.deleteByToken(token);
    }

    @Override
    public Optional<TokenEntity> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
