package com.srt.FreelanceMarketplace.service.logic.impl;

import com.srt.FreelanceMarketplace.domain.dto.user.JwtResponse;
import com.srt.FreelanceMarketplace.domain.dto.user.SignInRequest;
import com.srt.FreelanceMarketplace.domain.dto.user.SignUpRequest;
import com.srt.FreelanceMarketplace.domain.entities.RoleEntity;
import com.srt.FreelanceMarketplace.domain.entities.TokenEntity;
import com.srt.FreelanceMarketplace.domain.entities.UserEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.UserMapper;
import com.srt.FreelanceMarketplace.service.entity.RoleService;
import com.srt.FreelanceMarketplace.service.entity.TokenService;
import com.srt.FreelanceMarketplace.service.entity.UserService;
import com.srt.FreelanceMarketplace.service.logic.AuthService;
import com.srt.FreelanceMarketplace.service.logic.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

        RoleEntity role = roleService.getByName("ROLE_USER");
        entity.setRoles(List.of(role));

        userService.save(entity);
    }
}
