package com.srt.FreelanceMarketplace.service.entity.service;

import com.srt.FreelanceMarketplace.repository.service.ServiceImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceImageService {
    private final ServiceImageRepository repository;
}
