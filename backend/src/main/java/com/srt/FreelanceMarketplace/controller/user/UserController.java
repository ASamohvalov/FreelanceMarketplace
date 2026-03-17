package com.srt.FreelanceMarketplace.controller.user;

import com.srt.FreelanceMarketplace.domain.dto.request.FreelancerRequest;
import com.srt.FreelanceMarketplace.domain.dto.request.user.JwtRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserInfoResponse;
import com.srt.FreelanceMarketplace.service.entity.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
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
        Optional<File> file = userService.getAvatar(userId);
        if (file.isEmpty()) {
            return ResponseEntity.ok().build();
        }
        Resource resource = new FileSystemResource(file.get());
        String contentType = determineContentType(file.get().toPath());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
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

    private String determineContentType(Path path) {
        try {
            String type = Files.probeContentType(path);
            return type != null ? type : "application/octet-stream";
        } catch (IOException e) {
            return "application/octet-stream";
        }
    }
}
