package com.srt.FreelanceExchange.service.logic;

import com.srt.FreelanceExchange.domain.dto.user.JwtResponse;
import com.srt.FreelanceExchange.domain.dto.user.SignInRequest;
import com.srt.FreelanceExchange.domain.dto.user.SignUpRequest;

public interface AuthService {
    JwtResponse signIn(SignInRequest request);
    void signUp(SignUpRequest request);
}
