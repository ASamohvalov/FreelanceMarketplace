package com.srt.FreelanceMarketplace.controller;

import com.srt.FreelanceMarketplace.domain.dto.response.FreelancerResponse;
import com.srt.FreelanceMarketplace.service.entity.FreelancerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/freelancer")
@RequiredArgsConstructor
public class FreelancerController {
    private final FreelancerService freelancerService;

    @GetMapping("/get_all")
    public List<FreelancerResponse> getAll() {
        return freelancerService.getAll();
    }
}
