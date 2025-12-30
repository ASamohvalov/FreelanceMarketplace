package com.srt.FreelanceMarketplace.controller.user;

import com.srt.FreelanceMarketplace.domain.dto.request.user.JwtRequest;
import com.srt.FreelanceMarketplace.service.entity.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/logout")
    public void logout(@RequestBody @Valid JwtRequest request) {
        userService.logout(request);
    }

    @GetMapping("/get_info")
    public void getInfo() {

    }
}
