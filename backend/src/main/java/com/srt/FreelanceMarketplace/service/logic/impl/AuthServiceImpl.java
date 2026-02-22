package com.srt.FreelanceMarketplace.service.logic.impl;

import com.srt.FreelanceMarketplace.domain.dto.RoleEnum;
import com.srt.FreelanceMarketplace.domain.dto.request.user.JwtRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.user.JwtResponse;
import com.srt.FreelanceMarketplace.domain.dto.request.user.SignInRequest;
import com.srt.FreelanceMarketplace.domain.dto.request.user.SignUpRequest;
import com.srt.FreelanceMarketplace.domain.entities.user.RoleEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.TokenEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.UserMapper;
import com.srt.FreelanceMarketplace.service.entity.user.RoleService;
import com.srt.FreelanceMarketplace.service.entity.user.TokenService;
import com.srt.FreelanceMarketplace.service.entity.user.UserService;
import com.srt.FreelanceMarketplace.service.logic.AuthService;
import com.srt.FreelanceMarketplace.service.logic.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final TokenService tokenService;
    private final RoleService roleService;

    @Override
    public JwtResponse signIn(SignInRequest request) {
        UserEntity user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new GlobalBadRequestException("incorrect email or password"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new GlobalBadRequestException("incorrect email or password");
        }
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        TokenEntity tokenEntity = TokenEntity.builder()
                .token(refreshToken)
                .user(user)
                .build();
        tokenService.save(tokenEntity);

        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public void signUp(SignUpRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new GlobalBadRequestException("this email already taken");
        }
        UserEntity entity = userMapper.signUpRequestToEntity(request);
        entity.setPassword(passwordEncoder.encode(request.getPassword()));

        RoleEntity role = roleService.getByName(RoleEnum.ROLE_USER);
        entity.setRoles(List.of(role));

        userService.save(entity);
    }

    @Override
    @Transactional
    public JwtResponse updateTokens(JwtRequest request) {
        if (!jwtService.validateRefreshToken(request.getRefreshToken())) {
            throw new GlobalBadRequestException("this token not valid");
        }
        TokenEntity token = tokenService.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new GlobalBadRequestException("such token not found"));
        UserEntity user = token.getUser();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        token.setToken(refreshToken);

        return new JwtResponse(accessToken, refreshToken);
    }
}
