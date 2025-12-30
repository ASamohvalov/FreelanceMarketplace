package com.srt.FreelanceMarketplace.controller;

import com.srt.FreelanceMarketplace.domain.dto.request.ServiceRequest;
import com.srt.FreelanceMarketplace.service.logic.FreelancerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/freelancer")
@RequiredArgsConstructor
public class FreelancerController {
    private final FreelancerService freelancerService;

    @PostMapping("/service/add")
    public void addService(ServiceRequest request) {
        freelancerService.addService(request);
    }
}
