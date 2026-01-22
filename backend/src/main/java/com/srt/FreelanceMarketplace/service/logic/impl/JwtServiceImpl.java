package com.srt.FreelanceMarketplace.service.logic.impl;

import com.srt.FreelanceMarketplace.domain.entities.user.RoleEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.service.logic.JwtService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    private final SecretKey refreshSecret;
    private final SecretKey accessSecret;
    private final int refreshExpDays;
    private final int accessExpMins;

    public JwtServiceImpl(
            @Value("${token.refresh.secret}") String refreshSecret,
            @Value("${token.access.secret}") String accessSecret,
            @Value("${token.refresh.exp-days}") int refreshExpDays,
            @Value("${token.access.exp-mins}") int accessExpMins
    ) {
        this.refreshSecret = stringToSecret(refreshSecret);
        this.accessSecret = stringToSecret(accessSecret);
        this.refreshExpDays = refreshExpDays;
        this.accessExpMins = accessExpMins;
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
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            log.warn("access token not valid");
            return false;
        }
    }

    @Override
    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(accessSecret)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            log.warn("refresh token not valid");
            return false;
        }
    }

    @Override
    public String getSubjectFromAccessToken(String token) {
        return Jwts.parser()
                .verifyWith(accessSecret)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private SecretKey stringToSecret(String base64Str) {
        byte[] keyBytes = Decoders.BASE64.decode(base64Str);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateToken(UserEntity user, SecretKey key, Date exp) {
        List<String> roles = user.getRoles().stream()
                .map(RoleEntity::getName)
                .toList();
        return Jwts.builder()
                .claim("id", user.getId().toString())
                .claim("roles", roles)
                .subject(user.getEmail())
                .issuedAt(Date.from(Instant.now()))
                .expiration(exp)
                .signWith(key)
                .compact();
    }
}
