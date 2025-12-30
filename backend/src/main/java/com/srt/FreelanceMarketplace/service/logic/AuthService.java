package com.srt.FreelanceMarketplace.service.logic;

import com.srt.FreelanceMarketplace.domain.dto.request.user.JwtRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.user.JwtResponse;
import com.srt.FreelanceMarketplace.domain.dto.request.user.SignInRequest;
import com.srt.FreelanceMarketplace.domain.dto.request.user.SignUpRequest;

public interface AuthService {
    JwtResponse signIn(SignInRequest request);
    void signUp(SignUpRequest request);
    JwtResponse updateTokens(JwtRequest request);
}
