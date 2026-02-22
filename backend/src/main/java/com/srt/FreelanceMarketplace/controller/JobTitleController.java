package com.srt.FreelanceMarketplace.controller;

import com.srt.FreelanceMarketplace.domain.dto.request.JobTitleRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.JobTitleResponse;
import com.srt.FreelanceMarketplace.service.entity.user.JobTitleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job_title")
@RequiredArgsConstructor
public class JobTitleController {
    private final JobTitleService jobTitleService;

    @GetMapping("/get_all")
    public List<JobTitleResponse> getAll() {
        return jobTitleService.getAll();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void create(@RequestBody @Valid JobTitleRequest request) {
        jobTitleService.create(request);
    }
}
