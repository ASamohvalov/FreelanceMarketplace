package com.srt.FreelanceMarketplace.controller.user;

import com.srt.FreelanceMarketplace.domain.dto.user.JwtResponse;
import com.srt.FreelanceMarketplace.domain.dto.user.SignInRequest;
import com.srt.FreelanceMarketplace.domain.dto.user.SignUpRequest;
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
}
