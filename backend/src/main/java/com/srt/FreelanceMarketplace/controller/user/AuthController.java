package com.srt.FreelanceMarketplace.controller.user;

import com.srt.FreelanceMarketplace.domain.dto.request.user.JwtRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.user.JwtResponse;
import com.srt.FreelanceMarketplace.domain.dto.request.user.SignInRequest;
import com.srt.FreelanceMarketplace.domain.dto.request.user.SignUpRequest;
import com.srt.FreelanceMarketplace.service.logic.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign_in")
    public JwtResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authService.signIn(request);
    }

    @PostMapping("/sign_up")
    public void signUp(@RequestBody @Valid SignUpRequest request) {
        authService.signUp(request);
    }

    @PostMapping("update_tokens")
    public JwtResponse updateTokens(@RequestBody @Valid JwtRequest request) {
        return authService.updateTokens(request);
    }
}
