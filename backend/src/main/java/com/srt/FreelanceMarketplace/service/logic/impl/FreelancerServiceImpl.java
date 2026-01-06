package com.srt.FreelanceMarketplace.service.logic.impl;

import com.srt.FreelanceMarketplace.domain.dto.request.ServiceRequest;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.ServiceEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.mapper.FreelanceMapper;
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
    private final FreelanceMapper freelanceMapper;
    private final AuthHelperService authHelperService;

    @Override
    public void save(FreelancerEntity freelancer) {
        repository.save(freelancer);
    }

    @Override
    public void addService(ServiceRequest request) {
        ServiceEntity serviceEntity = freelanceMapper.serviceRequestToEntity(request);
        FreelancerEntity freelancer = repository.findByUser(authHelperService.getUser())
                        .orElseThrow(() -> new IllegalStateException("User has FREELANCER_ROLE but hasn't freelancer entity"));
        serviceEntity.setFreelancer(freelancer);
        serviceEntityService.save(serviceEntity);
    }

    @Override
    public boolean existsByUser(UserEntity user) {
        return repository.existsByUser(user);
    }
}
