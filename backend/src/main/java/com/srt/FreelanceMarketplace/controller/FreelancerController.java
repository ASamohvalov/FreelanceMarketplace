package com.srt.FreelanceMarketplace.controller;

import com.srt.FreelanceMarketplace.domain.dto.request.freelancer.EditFreelancerProfileRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.FreelancerResponse;
import com.srt.FreelanceMarketplace.service.application.user.FreelancerService;
import com.srt.FreelanceMarketplace.service.domain.user.FreelancerDomainService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/freelancer")
@RequiredArgsConstructor
public class FreelancerController {
    private final FreelancerService freelancerService;

    @GetMapping("/get")
    public List<FreelancerResponse> getAll() {
        return freelancerService.getAll();
    }

    @GetMapping("/get/{freelancerId}")
    public FreelancerResponse getById(@PathVariable UUID freelancerId) {
        return freelancerService.getById(freelancerId);
    }

    @GetMapping("/get/by_user/{userId}")
    public FreelancerResponse getByUserId(@PathVariable UUID userId) {
        return freelancerService.getByUserId(userId);
    }

    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    @PutMapping("/profile/edit")
    public void editProfile(@RequestBody @Valid EditFreelancerProfileRequest request) {
        freelancerService.editProfile(request);
    }
}
