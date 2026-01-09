package com.srt.FreelanceMarketplace.controller;

import com.srt.FreelanceMarketplace.domain.dto.request.ServiceRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.ServiceResponse;
import com.srt.FreelanceMarketplace.service.entity.ServiceEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceEntityService service;

    @GetMapping("/get_all")
    public List<ServiceResponse> getAll() {
        return service.getAll();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public void addService(ServiceRequest request) {
        service.create(request);
    }
}
