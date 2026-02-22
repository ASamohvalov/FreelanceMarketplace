package com.srt.FreelanceMarketplace.service.entity.user;

import com.srt.FreelanceMarketplace.domain.entities.user.TokenEntity;
import com.srt.FreelanceMarketplace.repository.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public void save(TokenEntity entity) {
        tokenRepository.save(entity);
    }

    @Transactional
    public void deleteByToken(String token) {
        tokenRepository.deleteByToken(token);
    }

    public Optional<TokenEntity> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
