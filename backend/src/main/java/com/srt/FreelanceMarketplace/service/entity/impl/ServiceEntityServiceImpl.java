package com.srt.FreelanceMarketplace.service.entity.impl;

import com.srt.FreelanceMarketplace.domain.dto.request.ServiceRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.ServiceResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserServiceResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.ServiceEntity;
import com.srt.FreelanceMarketplace.mapper.FreelanceMapper;
import com.srt.FreelanceMarketplace.repository.ServiceRepository;
import com.srt.FreelanceMarketplace.service.entity.ServiceEntityService;
import com.srt.FreelanceMarketplace.service.logic.AuthHelperService;
import com.srt.FreelanceMarketplace.service.logic.FreelancerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceEntityServiceImpl implements ServiceEntityService {
    private final ServiceRepository repository;
    private final FreelanceMapper freelanceMapper;
    private final FreelancerService freelancerService;
    private final AuthHelperService authHelperService;

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

    @Override
    public void create(ServiceRequest request) {
        ServiceEntity serviceEntity = freelanceMapper.serviceRequestToEntity(request);
        FreelancerEntity freelancer = freelancerService.findByUser(authHelperService.getUser())
                .orElseThrow(() -> new IllegalStateException("User has FREELANCER_ROLE but hasn't freelancer entity"));
        serviceEntity.setFreelancer(freelancer);
        repository.save(serviceEntity);
    }

    @Override
    public List<UserServiceResponse> getAllByFreelancer(FreelancerEntity entity) {
        return repository.getAllByFreelancer(entity).stream()
                .map(freelanceMapper::entityToUserServiceResponse)
                .toList();
    }
}
