package com.srt.FreelanceMarketplace.controller.user;

import com.srt.FreelanceMarketplace.domain.dto.request.FreelancerRequest;
import com.srt.FreelanceMarketplace.domain.dto.request.user.JwtRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserInfoResponse;
import com.srt.FreelanceMarketplace.service.entity.user.UserService;
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

    @PostMapping("/become_freelancer")
    public void becomeFreelancer(@RequestBody @Valid FreelancerRequest request) {
        userService.becomeFreelancer(request);
    }

    @GetMapping("/get_info")
    public UserInfoResponse getInfo() {
        return userService.getInfo();
    }
}
