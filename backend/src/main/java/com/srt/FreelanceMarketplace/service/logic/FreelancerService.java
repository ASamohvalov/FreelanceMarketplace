package com.srt.FreelanceMarketplace.service.logic;

import com.srt.FreelanceMarketplace.domain.dto.request.ServiceRequest;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;

public interface FreelancerService {
    void save(FreelancerEntity freelancer);
    void addService(ServiceRequest request);
    boolean existsByUser(UserEntity user);
}
