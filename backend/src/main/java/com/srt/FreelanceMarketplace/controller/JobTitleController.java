package com.srt.FreelanceMarketplace.controller;

import com.srt.FreelanceMarketplace.domain.dto.IdentifierDto;
import com.srt.FreelanceMarketplace.domain.dto.request.freelancer.JobTitleRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.JobTitleResponse;
import com.srt.FreelanceMarketplace.service.application.user.JobTitleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/job_title")
@RequiredArgsConstructor
public class JobTitleController {
    private final JobTitleService jobTitleService;

    @GetMapping("/get")
    public List<JobTitleResponse> getAll() {
        return jobTitleService.getAll();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public IdentifierDto create(@RequestBody @Valid JobTitleRequest request) {
        return jobTitleService.create(request);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable UUID id) {
        jobTitleService.delete(id);
    }
}
