package com.srt.FreelanceMarketplace.service.entity.impl;

import com.srt.FreelanceMarketplace.domain.dto.ServiceResponse;
import com.srt.FreelanceMarketplace.mapper.ServiceMapper;
import com.srt.FreelanceMarketplace.repository.ServiceRepository;
import com.srt.FreelanceMarketplace.service.entity.ServiceEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceEntityServiceImpl implements ServiceEntityService {
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    @Override
    public List<ServiceResponse> getAll() {
        return serviceRepository.findAllWithFreelancer().stream()
                .map(serviceMapper::serviceEntityToResponse)
                .toList();
    }
}
