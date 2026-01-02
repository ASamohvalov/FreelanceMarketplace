package com.srt.FreelanceMarketplace.service.entity.impl;

import com.srt.FreelanceMarketplace.domain.dto.response.ServiceResponse;
import com.srt.FreelanceMarketplace.domain.entities.ServiceEntity;
import com.srt.FreelanceMarketplace.mapper.FreelanceMapper;
import com.srt.FreelanceMarketplace.repository.ServiceRepository;
import com.srt.FreelanceMarketplace.service.entity.ServiceEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceEntityServiceImpl implements ServiceEntityService {
    private final ServiceRepository repository;
    private final FreelanceMapper freelanceMapper;

    @Override
    public void save(ServiceEntity service) {
        repository.save(service);
    }

    @Override
    public List<ServiceResponse> getAll() {
        return repository.findAllWithFreelancer().stream()
                .map(freelanceMapper::serviceEntityToResponse)
                .toList();
    }
}
