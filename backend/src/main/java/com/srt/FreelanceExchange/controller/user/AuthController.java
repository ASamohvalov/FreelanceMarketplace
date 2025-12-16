package com.srt.FreelanceExchange.controller.user;

import com.srt.FreelanceExchange.domain.dto.user.JwtResponse;
import com.srt.FreelanceExchange.domain.dto.user.SignInRequest;
import com.srt.FreelanceExchange.domain.dto.user.SignUpRequest;
import com.srt.FreelanceExchange.service.logic.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("sign_in")
    public JwtResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authService.signIn(request);
    }

    @PostMapping("sign_up")
    public void signUp(@RequestBody @Valid SignUpRequest request) {
        authService.signUp(request);
    }
}
