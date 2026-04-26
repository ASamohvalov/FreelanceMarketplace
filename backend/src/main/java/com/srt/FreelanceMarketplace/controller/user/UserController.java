package com.srt.FreelanceMarketplace.controller.user;

import com.srt.FreelanceMarketplace.domain.dto.request.freelancer.FreelancerRequest;
import com.srt.FreelanceMarketplace.domain.dto.request.user.EditUserProfileRequest;
import com.srt.FreelanceMarketplace.domain.dto.request.user.JwtRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserInfoResponse;
import com.srt.FreelanceMarketplace.service.application.user.UserService;
import com.srt.FreelanceMarketplace.util.FileHelperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final FileHelperUtil fileHelperUtil;

    @PostMapping("/logout")
    public void logout(@RequestBody @Valid JwtRequest request) {
        userService.logout(request);
    }

    @PostMapping("/become_freelancer")
    public void becomeFreelancer(@RequestBody @Valid FreelancerRequest request) {
        userService.becomeFreelancer(request);
    }

    @GetMapping("/avatar/{userId}")
    public ResponseEntity<Resource> getAvatar(@PathVariable UUID userId) {
        Optional<Path> path = userService.getAvatar(userId);
        if (path.isEmpty()) {
            return ResponseEntity.ok().build();
        }
        Resource resource = new FileSystemResource(path.get());
        return ResponseEntity.ok()
                .contentType(fileHelperUtil.getContentType(path.get()))
                .body(resource);
    }

    @PostMapping(value = "/avatar/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadAvatar(@ModelAttribute @RequestParam("avatar") MultipartFile avatar) {
        userService.uploadAvatar(avatar);
    }

    @GetMapping("/get_info")
    public UserInfoResponse getInfo() {
        return userService.getInfo();
    }

    @PutMapping("/profile/edit")
    public void editProfile(@RequestBody @Valid EditUserProfileRequest request) {
        userService.editProfile(request);
    }
}
