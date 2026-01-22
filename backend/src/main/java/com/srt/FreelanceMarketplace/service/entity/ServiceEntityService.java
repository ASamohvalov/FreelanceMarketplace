package com.srt.FreelanceMarketplace.service.entity;

import com.srt.FreelanceMarketplace.domain.dto.request.ServiceRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.ServiceResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserServiceResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;

import java.util.List;
import java.util.UUID;

public interface ServiceEntityService {
    List<ServiceResponse> getAll();
    void create(ServiceRequest request);
    List<UserServiceResponse> getAllByFreelancer(FreelancerEntity entity);
    byte[] getImage(UUID serviceId);
}
