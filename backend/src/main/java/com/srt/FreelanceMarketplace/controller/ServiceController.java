package com.srt.FreelanceMarketplace.controller;

import com.srt.FreelanceMarketplace.domain.dto.request.ServiceRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.ServiceResponse;
import com.srt.FreelanceMarketplace.service.entity.ServiceEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceEntityService service;

    @GetMapping("/get_all")
    public List<ServiceResponse> getAll() {
        return service.getAll();
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public void addService(@ModelAttribute @Valid ServiceRequest request) {
        service.create(request);
    }

    @GetMapping("/image/{serviceId}")
    public byte[] getImage(@PathVariable String serviceId) {
        return service.getImage(UUID.fromString(serviceId));
    }
}
