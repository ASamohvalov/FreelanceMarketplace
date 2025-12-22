package com.srt.FreelanceExchange.service.logic.impl;

import com.srt.FreelanceExchange.domain.dto.user.JwtResponse;
import com.srt.FreelanceExchange.domain.dto.user.SignInRequest;
import com.srt.FreelanceExchange.domain.dto.user.SignUpRequest;
import com.srt.FreelanceExchange.domain.entities.UserEntity;
import com.srt.FreelanceExchange.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceExchange.mapper.UserMapper;
import com.srt.FreelanceExchange.service.entity.UserService;
import com.srt.FreelanceExchange.service.logic.AuthService;
import com.srt.FreelanceExchange.service.logic.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtResponse signIn(SignInRequest request) {
        UserEntity user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new GlobalBadRequestException("incorrect email or password"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new GlobalBadRequestException("incorrect email or password");
        }
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public void signUp(SignUpRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new GlobalBadRequestException("this email already taken");
        }
        UserEntity entity = userMapper.signUpRequestToEntity(request);
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.save(entity);
    }
}
