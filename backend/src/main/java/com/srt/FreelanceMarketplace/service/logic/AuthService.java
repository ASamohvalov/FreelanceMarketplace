package com.srt.FreelanceMarketplace.service.logic;

import com.srt.FreelanceMarketplace.domain.dto.user.JwtResponse;
import com.srt.FreelanceMarketplace.domain.dto.user.SignInRequest;
import com.srt.FreelanceMarketplace.domain.dto.user.SignUpRequest;

public interface AuthService {
    JwtResponse signIn(SignInRequest request);
    void signUp(SignUpRequest request);
}
