package com.srt.FreelanceMarketplace.controller;

import com.srt.FreelanceMarketplace.domain.dto.ServiceResponse;
import com.srt.FreelanceMarketplace.service.entity.ServiceEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
}
