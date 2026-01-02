package com.srt.FreelanceMarketplace.service.logic;

import com.srt.FreelanceMarketplace.domain.dto.request.ServiceRequest;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;

public interface FreelancerService {
    void save(FreelancerEntity freelancer);
    void addService(ServiceRequest request);
}
