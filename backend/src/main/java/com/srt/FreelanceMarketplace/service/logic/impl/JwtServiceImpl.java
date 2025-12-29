package com.srt.FreelanceMarketplace.service.logic.impl;

import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.service.logic.JwtService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    private final SecretKey refreshSecret;
    private final SecretKey accessSecret;

    @Value("${token.refresh.exp-days}")
    private int refreshExpDays;
    @Value("${token.access.exp-mins}")
    private int accessExpMins;

    public JwtServiceImpl(
        @Value("${token.refresh.secret}") String refreshSecret,
        @Value("${token.access.secret}") String accessSecret
    ) {
        this.refreshSecret = stringToSecret(refreshSecret);
        this.accessSecret = stringToSecret(accessSecret);
    }

    @Override
    public String generateAccessToken(UserEntity user) {
        return generateToken(user, accessSecret,
                Date.from(Instant.now().plus(accessExpMins, ChronoUnit.MINUTES)));
    }

    @Override
    public String generateRefreshToken(UserEntity user) {
        return generateToken(user, refreshSecret,
                Date.from(Instant.now().plus(refreshExpDays, ChronoUnit.DAYS)));
    }

    @Override
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(accessSecret)
                    .build()
                    .parse(token);
            return true;
        } catch (JwtException e) {
            log.warn("JwtServiceImpl: token not valid");
            return false;
        }
    }

    @Override
    public String getSubject(String token) {
        return Jwts.parser()
                .verifyWith(accessSecret)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private SecretKey stringToSecret(String str) {
        return Keys.hmacShaKeyFor(str.getBytes());
    }

    private String generateToken(UserEntity user, SecretKey key, Date exp) {
        Map<String, String> claims = Map.of(
                "id", user.getId().toString()
                // todo: add roles
        );
        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .issuedAt(Date.from(Instant.now()))
                .expiration(exp)
                .signWith(key)
                .compact();
    }
}
