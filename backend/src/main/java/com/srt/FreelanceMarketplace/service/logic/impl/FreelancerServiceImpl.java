package com.srt.FreelanceMarketplace.service.logic.impl;

import com.srt.FreelanceMarketplace.domain.dto.request.ServiceRequest;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.ServiceEntity;
import com.srt.FreelanceMarketplace.mapper.ServiceMapper;
import com.srt.FreelanceMarketplace.repository.FreelancerRepository;
import com.srt.FreelanceMarketplace.service.entity.ServiceEntityService;
import com.srt.FreelanceMarketplace.service.logic.AuthHelperService;
import com.srt.FreelanceMarketplace.service.logic.FreelancerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FreelancerServiceImpl implements FreelancerService {
    private final FreelancerRepository repository;
    private final ServiceEntityService serviceEntityService;
    private final ServiceMapper serviceMapper;
    private final AuthHelperService authHelperService;

    @Override
    public void addService(ServiceRequest request) {
        ServiceEntity serviceEntity = serviceMapper.serviceRequestToEntity(request);
        FreelancerEntity freelancer = repository.findByUser(authHelperService.getUser())
                        .orElseThrow(() -> new IllegalStateException("User has FREELANCER_ROLE but hasn't freelancer entity"));
        serviceEntity.setFreelancer(freelancer);
        serviceEntityService.save(serviceEntity);
    }
}
