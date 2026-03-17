package com.srt.FreelanceMarketplace.service.domain.service;

import com.srt.FreelanceMarketplace.repository.service.ServiceImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceImageDomainService {
    private final ServiceImageRepository repository;
}
